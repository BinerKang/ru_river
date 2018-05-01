$(function () {
	var ctx = null;
	
	window.requestAnimFrame = (function() {
		return window.requestAnimationFrame || window.webkitRequestAnimationFrame ||
			window.mozRequestAnimationFrame || window.oRequestAnimationFrame ||
			window.msRequestAnimationFrame ||
			function(callback) {
				window.setTimeout(callback, 1000 / 60);
			};
	})();
	
	var Game = {
		
		status: true,
		
		canvas: document.getElementById('canvas'),
		
		setup: function(){
			if(this.canvas.getContext) {
				ctx = this.canvas.getContext('2d');
				
				this.width = this.canvas.width;
				this.height = this.canvas.height;
				
				Screen.welcome();
				this.canvas.addEventListener('click', this.runGame, false);
				Ctrl.init();
			}
		},
		
		runGame: function() {
			// 移除监听
			Game.canvas.removeEventListener('click', Game.runGame, false);
			Game.init();	
			Game.animate();			
		},
		
		startGame: function(){
			Game.canvas.removeEventListener('click', Game.startGame, false);
			Game.status = true;
			Game.levelUp();
		},
		
		restartGame: function() {
			// 移除监听
			Game.canvas.removeEventListener('click', Game.restartGame, false);
			Game.init();
		},
		
		animate: function() {
			Game.play = requestAnimFrame(Game.animate);
			if (Game.status) {
				Game.draw();
			}			
		},
		
		init: function() {
			Background.init();
			Hud.init();
			Ball.init();
			Paddle.init();
			Bricks.init();
			
		},
		
		draw: function() {
			ctx.clearRect(0, 0, this.width, this.height);
			
			Background.draw();
			Hud.draw();
			Bricks.draw();
			Paddle.draw();
			Ball.draw();
		},
		
		levelUp: function(){
			Hud.lv ++;
			Bricks.init();
			Ball.init();
			Paddle.init();
		},
		
		levelLimit: function(lv){//砖块限定为最高只能达到5行
			return lv > 5 ? 5 : lv;
		}
	};
	
	var Screen = {
		welcome: function(){
			this.text = 'CANVAS 弹球';
			this.textSub = '点击开始';
			this.textColor = 'white';
			
			this.create();
		},
		
		create: function(){
			ctx.fillStyle = 'black';
			ctx.fillRect(0, 0, Game.width, Game.height);
			ctx.fillStyle = this.textColor;
			ctx.textAlign = 'center';
			ctx.font = '40px Microsoft YaHei';
			ctx.fillText(this.text, Game.width / 2, Game.height / 2);
			
			ctx.fillStyle = '#99999';
			ctx.font = '20px Microsoft YaHei';
			ctx.fillText(this.textSub, Game.width / 2, (Game.height / 2 + 40));
		},
		
		stopgame: function(){
			Game.status = false;
			this.text = '恭喜过关';
			this.textSub = '点击继续';
			this.textColor = 'blue';
			this.create();										
		},
		
		gameover: function() {
			this.text = ' 游 戏 结 束';
			this.textSub = '点击再来';
			this.textColor = 'red';			
			this.create();
		}
	};
	
	var Background = {
		init: function(){
			this.ready = false;
			this.img = new Image();
			this.img.src = 'static/images/game_background.jpg';
			
			this.img.onload = function() {
				Background.ready = true;
			};
		},
		draw: function(){
			if (this.ready) {
				ctx.drawImage(this.img, 0, 0);
			}
		}
	};
	
	var Bricks = {
		gap: 2,
		col: 5,
		w: 80,
		h: 15,
		
		init: function(){
			this.row = 2 + Game.levelLimit(Hud.lv);
			this.disTotal = 0;// 消失的块数量
			
			this.count = [];
			for (var i = this.row; i--;) {
				this.count[i] = [this.col];
			}
		},
		
		draw: function(){
			var i, j, cx, cy ;
			for(i = this.row; i--;) {
				for (j = this.col; j--;){
					if(this.count[i][j] !== false) {
						cx = this.x(j);
						cy = this.y(i);
						if (Ball.x > cx && Ball.x < (cx + this.w) && Ball.y > cy && Ball.y < (cy + this.h)) {
							this.collide(i, j);
							continue;
						}
						ctx.fillStyle = this.gradient(i);
						ctx.fillRect(cx, cy, this.w, this.h);
					}
				}
			}
			if (this.disTotal === (this.row * this.col )) {
				Game.status = false;
				Ball.x = Ball.y = 1000;
				Ball.sx = Ball.sy = 0;
				Paddle.x = Paddle.y = 1000;
				Screen.stopgame();
				Game.canvas.addEventListener('click', Game.startGame, false);
				return;
			}
		},
		
		collide: function(i, j) {
			this.count[i][j] = false;
			Hud.score ++;
			Ball.sy = -Ball.sy;
			this.disTotal ++;
		},
		
		x: function(row){
			return row * (this.w + this.gap) 
		},
		
		y: function(col){
			return col * (this.h + this.gap);
		},
		
		gradient: function(row) {
			switch(row) {
				case 0:
					return this.gradientPurple?this.gradientPurple:this.gradientPurple = this.makeGradient(row, '#bd06f9', '#9604c7');
				case 1:
					return this.gradientRed?this.gradientRed:this.gradientRed = this.makeGradient(row, '#F9064A', '#c7043b');
				case 2:
					return this.gradientGreen?this.gradientGreen:this.gradientGreen = this.makeGradient(row, '#05fa15', '#04c711');
				default:
					return this.gradientOrange?this.gradientOrange:this.gradientOrange = this.makeGradient(row, '#faa105', '#c77f04');
			}
		},
		
		makeGradient: function(row, color1, color2){
			var y = this.y(row);
			var grad = ctx.createLinearGradient(0, y, 0, y + this.h);
			grad.addColorStop(0, color1);
			grad.addColorStop(1, color2);
			
			return grad;
		}
	};
	
	var Paddle = {
		w: 90,
		h: 20,
		r: 9,
		
		init: function(){
			this.x = 100;
			this.y = 210;
			this.speed = 4;
		},
		
		draw: function(){
			this. move();
			
			ctx.beginPath();
            ctx.moveTo(this.x, this.y);
            ctx.arcTo(this.x + this.w, this.y, this.x + this.w, this.y + this.r, this.r);
            ctx.arcTo(this.x + this.w, this.y + this.h, this.x + this.w - this.r, this.y + this.h, this.r);
            ctx.arcTo(this.x, this.y + this.h, this.x, this.y + this.h - this.r, this.r);
            ctx.arcTo(this.x, this.y, this.x + this.r, this.y, this.r);
            ctx.closePath();
            ctx.fillStyle = this.gradient();
            ctx.fill();
		},
		
		move: function() {
			if ( Ctrl.left && (this.x > -(this.w / 2))) {
				this.x -= this.speed;
			} else if (Ctrl.right && (this.x < Game.width - (this.w / 2))) {
				this.x += this.speed;
			}
		},
		
		gradient: function() {
            if (this.gradientCache) {
                return this.gradientCache;
            }

            this.gradientCache = ctx.createLinearGradient(this.x, this.y, this.x, this.y + 20);
            this.gradientCache.addColorStop(0, '#eee');
            this.gradientCache.addColorStop(1, '#999');

            return this.gradientCache;
        }
	};
	
	var Ball = {
		r: 10,
		init: function(){
			this.x = 120;
			this.y = 120;
			this.sx = 1.5 + 0.4 * Hud.lv;
			this.sy = -this.sx;
		},
		draw: function(){
			this.edges();
			this.collide();
			this.move();
			
			ctx.beginPath();
			ctx.arc(this.x, this.y, this.r, 0, 2 * Math.PI);
			ctx.closePath();
			ctx.fillStyle = '#eee';
			ctx.fill();
		},
		
		edges: function() {
			if(this.y < 1) { // 游戏上边界
				this.y = 1;
				this.sy = -this.sy;				
			} else if (this.y > Game.height && Game.status) { // 出界触发游戏结束
				this.sy = this.sx = 0;
				this.y = this.x = 1000;
				Screen.gameover();
				Game.canvas.addEventListener('click', Game.restartGame, false);
				return;
			} 
			if (this.x < 1) {
				this.x = 1;
				this.sx = -this.sx;
			} else if (this.x > Game.width) {
				this.x = Game.width - 1;
				this.sx = -this.sx;
			}
		},
		collide: function() {
			var flag = (this.x > (Paddle.x - this.r) && this.x < (Paddle.x + Paddle.w + this.r) && this.y < Paddle.y && this.y > (Paddle.y - this.r));
			if (flag) {
				this.sx = 7 * (this.x - (Paddle.x + Paddle.w / 2)) / Paddle.w;
				this.sy = -this.sy;
			}
		},
		move: function() {			
			this.x += this.sx;
			this.y += this.sy;
		}
	};
	
	var Ctrl = {
		init: function(){
			window.addEventListener('keydown', this.keyDown, true);
			window.addEventListener('keyup', this.keyUp, true);
			window.addEventListener('mousemove', this.movePaddle, true);
			
			Game.canvas.addEventListener('touchstart', this.movePaddle, false);
			Game.canvas.addEventListener('touchmove', this.movePaddle, false);
			Game.canvas.addEventListener('touchmove', this.stopTouchScroll, false);
			
		},
		
		keyDown: function(event){
			switch(event.keyCode){
				case 39 :
					Ctrl.right = true;
					break;
				case 37 :
					Ctrl.left = true;
					break;
				default:
					break;
			}
		},
		
		keyUp: function(event){
			switch(event.keyCode){
				case 39 :
					Ctrl.right = false;
					break;
				case 37 :
					Ctrl.left = false;
					break;
				default:
					break;
			}
		},
		
		movePaddle: function(event){
			var mouseX = event.pageX;
			var canvasX = Game.canvas.offsetLeft;
			
			var paddleMid = Paddle.w / 2;
			if (mouseX > canvasX && mouseX < (canvasX + Game.canvas.width)) {
				var newX = mouseX - canvasX - paddleMid;
				Paddle.x = newX;
			}
		},
		
		stopTouchScroll: function(event){
			event.preventDefault();
		}
	};
	
	var Hud = {
		init: function(){
			this.lv = 1;
			this.score = 0;
		},
		draw: function(){
			ctx.font = '12px Microsoft YaHei';
			ctx.fillStyle = 'white';
			ctx.textAlign = 'left';
			ctx.fillText('得分: ' + this.score, 5, Game.height - 5);
			ctx.textAlign = 'right';
			ctx.fillText('关卡: ' + this.lv, Game.width - 5, Game.height - 5);
		}
	};
	
	Game.setup();
});

/**
 * Created by Administrator on 2017/10/26 0026.
 */
    var Slideicon = function (element,options) {
        this.element = element;
        this.options = {
            cover:options.cover,
            index:options.index,
            callback:options.callback
        };
        this.init();
    };
    Slideicon.prototype.init = function () {
        var _this = this,
            params = {};
        this.element.on('click','li',function(){
            $(this).nextAll().removeClass('active');
            $(this).prevAll().removeClass('active');
            var width = $(this).width();
            var left = ($(this).index())*width;
            $(this).parent().prev().attr("style","left:"+left+"px");
            $(this).addClass("active");
            params.child = $(this).attr('type');
            params.father = $(this).parent().attr('id');
            _this.options.callback(params)
        });
    };


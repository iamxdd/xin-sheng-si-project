(function(a) {
    a.fn.vTicker = function(b) {
        var c = {
            speed: 700,
            pause: 4000,
            showItems: 8,
            animation: "",
            mousePause: true,
            isPaused: false,
            direction: "up",
            height: 0
        };
        var b = a.extend(c, b);
        moveUp = function(g, d, c) {
            var f = g.children("ul");
            var listheight = f.height();
            var containerheight = $(".city-table").height();
            if (c.isPaused || listheight<=containerheight) {
                return
            }
            var h = f.children("li:first").clone(true);
            var last = f.children("li:last");
            if (last.attr('class')){
                h.attr('class',"")
            }else {
                h.attr('class',"withbg")
            }
            if (c.height > 0) {
                d = f.children("li:first").height()
            }
            f.animate({
                    top: "-=" + d + "px"
                },
                c.speed,
                function() {
                    a(this).children("li:first").remove();
                    a(this).css("top", "0px")
                });
            if (c.animation == "fade") {
                f.children("li:first").fadeOut(c.speed);
                if (c.height == 0) {
                    f.children("li:eq(" + c.showItems + ")").hide().fadeIn(c.speed)
                }
            }
            h.appendTo(f)
        };
        return this.each(function() {
            var f = a(this);
            var e = 0;
            f.children("ul").css({
                position: "absolute",
                margin: 0,
                padding: 0
            }).children("li").css({
                margin: 0,
                padding: 0
            });
            if (b.height == 0) {
                f.children("ul").children("li").each(function() {
                    if (a(this).height() > e) {
                        e = a(this).height()
                    }
                });
                f.children("ul").children("li").each(function() {
                    a(this).height(e)
                });
                f.height(e * b.showItems)
            } else {
                f.height(b.height)
            }
            var d = setInterval(function() {
                    if (b.direction == "up") {
                        moveUp(f, e, b)
                    } else {
                        moveDown(f, e, b)
                    }
                },
                b.pause);
            if (b.mousePause) {
                f.bind("mouseenter",
                    function() {
                        b.isPaused = true
                    }).bind("mouseleave",
                    function() {
                        b.isPaused = false
                    })
            }
        })
    }
})(jQuery);
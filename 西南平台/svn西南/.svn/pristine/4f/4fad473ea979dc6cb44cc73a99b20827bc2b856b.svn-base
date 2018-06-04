/**
 * Created by Administrator on 2017/9/6 0006.
 */
Date.prototype.format = function (format) {
    var o = {
        'M+': this.getMonth() + 1,
        'd+': this.getDate(),
        'h+': this.getHours(),
        'H+': this.getHours(),
        'm+': this.getMinutes(),
        's+': this.getSeconds(),
        'q+': Math.floor((this.getMonth() + 3) / 3),
        'S': this.getMilliseconds()
    };
    if (/(y+)/.test(format)) { format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length)) }
    for (var k in o) {
        if (new RegExp('(' + k + ')').test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length === 1
                ? o[k]
                : ('00' + o[k]).substr(('' + o[k]).length))
        }
    }
    return format
};


var getChartDate = function (start,end,time) {
    var a = new Calendar(start,end,time);
    function Calendar(start,end,time) {
        var  date    = time?new Date(time.replace(/-/g,'/')):new Date();
        this.start   = 0;
        this.end     = end-start;
        this.time    = date.setDate(date.getDate() + start);
        this.arr     = [];
        this.init  = function () {
            var modelTime = new Date();
            if (this.time){
                modelTime = new Date(this.time)
            }
            var o = {
                'year'  : modelTime.getFullYear(),
                'month' : modelTime.getMonth() + 1,
                'date'  : modelTime.getDate(),
                'hour'  : modelTime.getHours(),
                'minute': modelTime.getMinutes(),
                'second': modelTime.getSeconds()
            };
            this.year  = o.year;
            this.month = o.month;
            this.day   = o.date;
            this.reset(this.start,this.end)
        };
        this.date = function () {
            var days = this.getAllDay();
            if (this.day>days){
                this.day = 1;
                if (this.month>=12){
                    this.month = 0;
                    this.year += 1
                }
                this.month +=1;
            }
            var obj   = {};
            obj.year  = this.year;
            obj.month = this.month;
            obj.day   = this.day;
            var time  = this.year+"/"+this.month+"/"+this.day;
            obj.level = this.getTimeStamp(time);
            this.arr.push(obj);
            this.day  += 1;
        };
        this.getTimeStamp = function (time) {
            var today = {
                'year'  : new Date().getFullYear(),
                'month' : new Date().getMonth() + 1,
                'date'  : new Date().getDate()
            };
            var now = today.year+"/"+today.month+"/"+today.date;
            var date = new Date(now.replace(/-/g,'/')).getTime()-new Date(time.replace(/-/g,'/')).getTime();
            if(date<0){
                return 2
            }else if (date==0){
                return 0
            }else {
                return 1
            }
        };
        this.getAllDay = function () {
            var d = new Date(this.year, this.month, 0);
            return d.getDate()
        };
        this.reset = function (start,end) {
            if (start<=end){
                start++;
                this.date(start);
                this.reset(start,end);
            }else {
            }
        };
        this.init()
    }
    return a.arr;
};

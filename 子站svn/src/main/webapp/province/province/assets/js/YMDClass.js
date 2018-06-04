SYT = "年份";
SMT = "月份";
BYN = 20; //年份范围往前20年
AYN = 0; //年份范围往后0年
function YMDselect() {
	this.SelY = document.getElementsByName(arguments[0])[0];
	this.SelM = document.getElementsByName(arguments[1])[0];
	this.SelD = document.getElementsByName(arguments[2])[0];
	this.DefY = this.SelD ? arguments[3] : arguments[2];
	this.DefM = this.SelD ? arguments[4] : arguments[3];
	this.DefD = this.SelD ? arguments[5] : arguments[4];
	this.SelY.YMD = this;
	this.SelM.YMD = this;
	this.SelY.onchange = function() {
		YMDselect.SetM(this.YMD)
	};
	if(this.SelD) this.SelM.onchange = function() {
		YMDselect.SetD(this.YMD)
	};
	YMDselect.SetY(this)
};
//设置年份
YMDselect.SetY = function(YMD) {
	dDate = new Date();
	dCurYear = dDate.getFullYear();
	YMD.SelY.options.add(new Option(SYT, '0'));
	for(i = dCurYear + AYN; i > (dCurYear - BYN); i--) {
		YMDYT = i;
		YMDYV = i;
		OptY = new Option(YMDYT, YMDYV);
		YMD.SelY.options.add(OptY);
		if(YMD.DefY == YMDYV) OptY.selected = true
	}
	YMDselect.SetM(YMD)
};
//设置月份
YMDselect.SetM = function(YMD) {
	YMD.SelM.length = 0;
	YMD.SelM.options.add(new Option(SMT, '0'));
	if(YMD.SelY.value > 0) {
		for(var i = 1; i <= 12; i++) {
			YMDMT = i;
			YMDMV = i;
			OptM = new Option(YMDMT, YMDMV);
			YMD.SelM.options.add(OptM);
			if(YMD.DefM == YMDMV) OptM.selected = true
		}
	}
	if(YMD.SelD) YMDselect.SetD(YMD)
};

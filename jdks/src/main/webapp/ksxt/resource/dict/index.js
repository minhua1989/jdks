
function init () {
	var pt = navigator.platform.toLowerCase();
	var ismac = (pt == "mac68k") || (pt == "macppc") || (pt == "macintosh") || (pt == "macintel");
	var s = '请按 Ctrl + f 键进行搜索';
	if (ismac) {
		s = '请按 command + f 键进行搜索';
	}
	$('.header .r').text(s);
}


init();



window.onload = function () {

  var t = ResultDate()

  var element = document.getElementsByClassName('pp')
  element[0].setAttribute('title', '系统停机维护中，预计' + t + '日开放')

  var ele = document.getElementsByClassName('info')
  ele[0].innerHTML = t
}

function ResultDate () {
  var d = new Date()
  return (d.getMonth() + 1) + '月' + d.getDate() + '日'
}
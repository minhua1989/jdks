$(function(){
  var $as=$(window.parent.document).find("nav a")

  $('.recent-articles a').on('click',function(e){
    e.preventDefault()
    var id=this.id
    var temp=id.split('.')
    $as.removeClass('active')
    var length=temp.length
    console.log(temp)
    var str=temp.reduce(function(sum,ch,index){
      if(index==length-1){
        return sum+ch
      }
        return sum+ch+'\\.'
    },'')
    console.log(str)
    parent.postMessage(str,'*')
  })
  

})

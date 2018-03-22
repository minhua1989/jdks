var json=require('./data/data.js')
// var obj=JSON.parse(json)
var obj=json.obj
for(var i in obj){
    console.log(i)
}
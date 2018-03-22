// Print all of the news items on Hacker News
var jsdom = require("jsdom");
var through=require('through2-map');
var escapeJSON=require('escape-json-node');

var fs=require('fs');
var jquery=fs.readFileSync('./node_modules/jquery/dist/jquery.min.js','utf-8');

var exec=require('child_process').exec;
var fileNames=[];
var data={}
exec('ls -pA ./articles',function(err,stdout,stderr){
    if(err)
        return console.error(err)
    fileNames=stdout.split('\n');
    fileNames=fileNames.filter(function(name){
        name=name.replace(/\s*/,'')
        return (name.indexOf('img'))==-1&&name!=''
    });

    fileNames.forEach(function(name,i){

        renderText(name,i,save);
    })
})

function save(i){
    if(fileNames.length-1==i){


        data=escapeJSON(JSON.stringify(data))
        console.log(data)

        fs.writeFile(__dirname+'/data/data.js',data,'utf8',function(err){
            console.error(err);
        })
    }
}





// fs.writeFile('./data/data.js',function(err){
//     console.error(err);
// })


function renderText(name,i,callback){
    jsdom.env({
        file:__dirname+'/articles/'+name,
        src:[jquery],
        done:function(err,window){
            if(err)
                return console.error(err);
            var $=window.$;
            var title=$('h2').text().replace(/\s*/,'').replace('\"','\"')
            var article=$('p').not('.contact-us').text().replace(/\s*/,'').replace('\"','\"')
            data[name]=title+article;

            callback(i)
        }
    })
}

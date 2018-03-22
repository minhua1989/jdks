$(function(){


    function BuildNav(parent,data){
        var keys=Object.keys(data)
        // sort in numeric ascending order
        keys.sort(compare)
        // console.log(keys)

        // outmost main-nav
        var $ul=elt('ul').addClass('main-nav')
        parent.append($ul)
        var current=null
        var cnt=0
        keys.forEach(function(key){
            var item=data[key]
            // get the loop length
            var loop=key.split('').filter(function(ch){
                return ch=='.'
            }).length
            var selectionStr='ul.main-nav'
            for(var i=0;i<loop;i++){
                selectionStr+='>li:last-of-type>ul'
            }
            selectionStr+=':last-of-type'
            $ul=$(selectionStr)
            // console.log(++cnt)
            // console.log(selectionStr)
            if(item.type=='desc'){
                $li=elt('li').append(elt('span').attr({'id':key}).text(key+'. '+item.title))
                $ul.append($li)
                $li.append(elt('ul'))
            }
            else if(item.type=='link')
            // give it an id for later retrieving from the iframe
                $ul.append(elt('li').append(elt('a').attr({'href':'articles/'+key+'.html','id':key}).text(key+'. '+item.title)))

        })
        // $('.main-nav li a:first-child').trigger('click');

    }


    BuildNav($('.wrapper nav'),navData)


    function compare(a,b){
        a=a.split('.')
        b=b.split('.')
        // loop end == min value of the length of two arrs
        for(var i=0;i<Math.min(a.length,b.length);i++){
            if(a[i]==b[i])
                continue
            return a[i]-b[i]
        }
        return a.length-b.length
    }


// register event
    $nav = $('.main-nav')
    $lis = $nav.find('li')
    $as=$nav.find('a')
    $iframe=$('iframe')
    $nav.on('click', 'span',function(e){
            fnShowHide.bind(this)(e)
        }
    )

    //toggle active class
    $nav.on('click','a',function(e){

        $as.removeClass('active')
        $(this).addClass('active')
        updateLink.bind(this)(e)
    })

//trigger first link click
    $lis.find('span:first-child')[0].click()
    $lis.find('a:first-child')[0].click()

    function fnShowHide (e) {
        // debugger;
        // console.log(this)
        $span = $(this)
        $li=$span.parent()
        var $ul = $span.next('ul')
        if($ul.is(':hidden')){
            $ul.show()
            // console.log($ul.children().first())
            //show each in stack order
            $ul.children().first().show(100,function fnShowNext(){
                $(this).next().show(100,fnShowNext)
            })
            $li.css({border: 'none'})
        }else{
            $ul.hide(100)
            $ul.children().hide(100)
            $li.css({borderBottom:''})
        }
    }

    function updateLink(e){
        e.preventDefault()
        $iframe.attr('src',$(this).attr('href'))
    }

// hold the article lists max-length:6 with last one set to display none
    var $articles=elt('ul').addClass('recent-articles')
    //hold link address max-length:5
    var articlesArr=[]

// push recent articles
    $nav.on('click','a',buildNewsList)

    function buildNewsList(e){
        // debugger;
        var text=$(this).text()
        // if link exists return
        if(articlesArr.indexOf(text)>=0)
            return

        if(articlesArr.length>=5)
            articlesArr.unshift()
        articlesArr.push(text)

        $li=$(this).parent().clone().attr('style','')
        $a=$li.find('a')
        $a.removeClass('active')

        // modify the address since its in iframe
        var href=$a.attr('href').replace(/\/?articles\/?/,'')
        // console.log(href)
        $a.attr('href',href)

        if($articles.children().length>=6){
            $articles.find('li:first-child').remove()
        }
        $articles.append($li)

    }



    // var $ul=ibody.find('.recent-articles ul')

    $iframe.on('load',function(){
        // debugger;
        //if no articles return
        if(!($articles.children().length))
            return;
        //insert article ul to iframe when loaded
        var ibody=$iframe.contents().find('body');
        ibody.find('.recent-articles').append(elt('h3').text('最近查看的文章')).append($articles);
        //register event

    })

//listen message handler get id and make click
    window.addEventListener('message',function(e){
        var str=''+e.data

        $('#'+str)[0].click()
    })


})

function elt(element){
    return $(document.createElement(element))
}


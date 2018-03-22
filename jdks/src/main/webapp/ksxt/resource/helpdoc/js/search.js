// textObj
$(function () {
    var $input = $('.search-wrapper input');
    var wrapper = null
    var mask = $('.mask')
    var val = ''
    $input.on('input', function (e) {
        // console.log('input changed')
        if (wrapper){
            wrapper.remove()
            wrapper=null
        }

        val = $input.val()
        if (val) {
            var match = false;
            wrapper = elt('ul').addClass('search-show').css({
                'display': 'none',
                'position': 'absolute'
            }).appendTo($('.search-wrapper'))

            for (var name in textObj) {
                // debugger;
                var article = textObj[name]
                function renderIndex(name){
                    return name.substring(0,name.lastIndexOf('.'))
                }
                var title = renderIndex(name)+'. '+article.substring(0, article.indexOf('\n'))
                var index = article.indexOf(val)

                var titleIsSet = false
                while (index != -1) {
                    match = true;
                    //substring handles boundary by default
                    var output = article.substring(index - 50, index + 50)

                    //view
                    // console.log(output)
                    output = output.replace(new RegExp(val, 'g'), function (match) {
                        return '<em>' + val + '</em>'
                    })
                    if (!titleIsSet) {
                        appendResult(wrapper, output, name, title)
                        titleIsSet = true
                    } else {
                        appendResult(wrapper, output, name)
                    }
                    article = article.substr(index + val.length + 50);
                    index = article.indexOf(val)
                }
            }
            
            if (match)//show search result only if theres a match
                wrapper.show()
            else{//defensive 
                wrapper.remove()
                wrapper=null
            }

            // $group=$(document).not('.search-show').add($('iframe').contents().find('body'));
        }


    });//input event close


    $('.search-wrapper').on('click','li,h2', function (e) {
        // console.log('search result clicked')
        var dataId = $(this).data().id
        var index = dataId.lastIndexOf('.')
        var dataId = dataId.substring(0, index)
        var temp=dataId.split('.')
        var spanStr=temp[0]
        $span=$('#'+spanStr)
        if($span.next('ul').is(':hidden')){
            $span.trigger('click')
            for(var i=1;i<temp.length-2;i++){
                spanStr+='\\.'+temp[1]
                 $span=$('#'+spanStr)
                if($span.next('ul').is(':hidden')){
                    $span.trigger('click')
                }
            }
        }



        dataId=temp.join('\\.')

        var $a=$('#' + dataId)

        $a.click()

        mask.trigger('click')

    })

    mask.on('click', function (e) {
        // console.log('mask clicked')
        if(wrapper)
            wrapper.remove()
        $input.val('')
        mask.css({
            visibility: 'hidden',
            opacity: 0
        })
    })


    $input.on('focus', function () {
        // console.log('input focused')
        mask.css({
            visibility: 'visible',
            opacity: 100
        })

    })


})

function appendResult(wrapper, result, link, name) {
    if (name)
        wrapper.append('<h2 data-id="' + link + '">' + name + '</h2>');

    wrapper.append('<li a data-id="' + link + '">' + result + '</li>')

}


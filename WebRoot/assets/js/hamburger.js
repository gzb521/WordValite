
$(function(){
    console.log("执行")
    menuToggle = function(e) {
        $(e).toggleClass('is_active');
    };
    menuInit = function(linkArr){
        var html = '<div id="hamburger" onclick="menuToggle(this)"><div><span class="line"></span><span class="line"></span><span class="line"></span></div><div class="menu_items">';
        for (var i = 0, links = ''; i<linkArr.length; i++){
            links += '<a href="' + linkArr[i].href + '">' + linkArr[i].name + '</a>';
        }
        html += links + '</div></div>';
        $('header').append(html);
    }
    console.log("执行完毕")

    menuInit([            
        {"href":"/index","name":"首页"}
    ]);
})
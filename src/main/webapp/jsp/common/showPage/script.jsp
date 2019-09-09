<%--
  Created by IntelliJ IDEA.
  User: wjx
  Date: 17-8-27
  Time: 下午9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    function fetchSvg(){
        $.ajax({
            url:'${url}',
            data:{articleId:articleId},
            type:'get',
            success:data=> {
                if (data.status === 200) {
                    data.data.forEach(ele => contents.push(ele.svg));
                    $('#page').html(contents[$("#number").text()]);
                }
            }
        })
    }
    let articleId = ${articleId};
    let contents = [''];
    fetchSvg();
    let pageCount = ${articleInfo.pageCount};
    $("#pageCount").text(pageCount);
    $("#next").click(()=>{
        let now = parseInt($('#number').text());//现在的页码
        if(now+1>pageCount){
            return;//TODO 翻到下一篇文章
        }
        $('#number').text(++now);
        if(now===contents.length){//现在的页码大于content中的长度
            fetchSvg();
        }else{
            $('#page').html(contents[now]);
        }
    })
    $('#previous').click(()=>{
        let now = parseInt($('#number').text());//现在的页码
        if(now===1){
            return;
        }
        $('#number').text(--now);
        $('#page').html(contents[now]);
    })
</script>

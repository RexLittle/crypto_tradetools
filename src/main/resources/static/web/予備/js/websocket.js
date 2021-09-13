<!-- 獲得したデータの数分の行を作成 -->

window.onload=function() {

alert(1);
var websocket = null;

// 判断当前游览器是否支持WebSocket
if("WebSocket" in window) {
    //创建WebSocket对象,连接服务器端点
    websocket = new WebSocket("wss://ws.okex.com:8443/ws/v5/public");
} else {
    alert("Not support websocket")
}

//连接发生错误的回调方法
websocket.onerror = function() {
    alert(1);
    appendMessage("error");
};

//连接成功建立的回调方法
websocket.onopen = function(event) {
    alert(2);
    appendMessage("open");
}

//接受到消息的回调方法
websocket.onmessage = function(event) {
    alert(3);
    appendMessage(event.data);
}

//连接关闭的回调方法
websocket.onclose = function() {
    alert(4);
    appendMessage("close");
}

//监听窗口关闭事件,当窗口关闭时,主动关闭websocket连接
//防止连接还没断开就关闭窗口,server端会抛出异常
window.onbeforeunload = function() {

    alert(5);
    websocket.close();
}

//将消息显示在网页上
function appendMessage(message) {
    alert(6);
    console.log(message);
}

//关闭连接
function closeWebSocket() {
    alert(7);
    websocket.close();
}

function sendMessage() {
    alert(8);
    var message = $("#message").val();
    websocket.send(message);
}}

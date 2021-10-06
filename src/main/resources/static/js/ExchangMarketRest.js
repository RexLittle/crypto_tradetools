var exchange = "binance";
var str;
var xhr;


//function addDataInTable
var tableBody = document.createElement("tbody");
var tr;
var td;

//table
var menu_btm;
var menu;


var array_temp;
var fileter_option = "USDT";
window.onload=function() {

    menu_btm = document.getElementById("in");
    menu = document.getElementById("show");

    <!-- ドロップダウンメニューにhover -->
    function hover(ele, fn1,fn2) {
        ele.onmouseenter = function(){
            fn1.call(ele);
        }
        document.getElementById("out").onmouseleave  = function() {
            fn2.call(ele);
        }
    }
    hover(
    menu_btm,
     function(){
     menu_btm.classList.add("show");
     menu_btm.setAttribute("data-bs-toggle","dropdown");
     menu_btm.setAttribute("aria-expanded","true");

     menu.classList.add("show");
     menu.style ="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 40px);";
     menu.setAttribute("data-popper-placement","bottom-start");
    },
    function(){
     menu_btm.classList.remove("show");
     menu_btm.removeAttribute("data-bs-toggle");
     menu_btm.removeAttribute("expanded");

     menu.classList.remove("show");
     menu.removeAttribute("style");
     menu.removeAttribute("data-popper-placement");

    })

    setInterval(()=>{
        if(window.XMLHttpRequest) {
           xhr = new XMLHttpRequest();
        } else {
           xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var url = "";
        switch(exchange){
            case "binance":
                url = "http://localhost:8080/byy/binanceMarket";
                break;

            case "okex":
                url = "http://localhost:8080/byy/okexMarket";
                break;

            case "kucoin":
                url = "http://localhost:8080/byy/kuCoinMarket";
                break;
        }
        xhr.open("GET",url,true);
        xhr.send();
            xhr.onreadystatechange = function() {
                  // readyState == 4リクエスト成功
              if(xhr.readyState==4) {
                  if(xhr.status==200 || xhr.status==304){

                      var jsonStr = xhr.responseText;
                      var jsonArray = eval('(' + jsonStr + ')');
                     addDataInTable(jsonArray);
                  }

              }

            }
    }, 3000);

}


<!-- テーブルの作成 -->
function addDataInTable(array) {
    array_temp = array;
    var new_array = array.filter(item => item["pair"].includes(fileter_option));
    sortChoose(new_array);
    requestAnimationFrame(()=>{
            tableBody.innerHTML="";
            var jsonName=new Array("pair","lastprice","_24hChanges","_24hVolume");
            tableBody.id = "myTable";
            table = document.getElementById("tb");
            var fragment = document.createDocumentFragment();
            fragment.appendChild(tableBody);
            if(tableBody.childNodes.length == 0){
            //             str = "";
                 for(i = 0; i < new_array.length; i++){
                          tr = document.createElement("tr");
                          for(j=1; j<=4; j++){
                              td = document.createElement("td");
                              td.innerText = new_array[i][jsonName[j-1]];
                              td.classList.add("d-none");
                              td.classList.add("d-lg-table-cell");
                               if(jsonName[j-1] == "_24hChanges"){
                                    td.innerText = td.innerText + "%";
                                    if(new_array[i][jsonName[j-1]] > 0){
                                        td.classList.add("price_up");
                                    }else{
                                        td.classList.add("price_down");
                                    }

                               }
                              tr.appendChild(td);
                          }
                            fragment.getElementById("myTable").appendChild(tr);
                 }
            }
                        table.appendChild(fragment);

    });
}

<!-- テーブルソートの選択 -->
function sortChoose(array){
    var pairClassName = document.getElementById("pair").className;
    var lastClassName = document.getElementById("last").className;
    var changeClassName = document.getElementById("change").className;
    var volumeClassName = document.getElementById("volume").className;

    if(pairClassName.includes("sort-up-dark")){
        array.sort((a,b)=>{
               return a["pair"].charCodeAt() - b["pair"].charCodeAt();
            })
    }else if(pairClassName.includes("sort-down-dark")){
        array.sort((a,b)=>{
           return b["pair"].charCodeAt() - a["pair"].charCodeAt();
        })
    }else if(lastClassName.includes("sort-up-dark")){
        array.sort((a,b)=>{
               return a["lastprice"] - b["lastprice"];
            })
    }else if(lastClassName.includes("sort-down-dark")){
         array.sort((a,b)=>{
            return b["lastprice"] - a["lastprice"];
         })
    }else if(changeClassName.includes("sort-up-dark")){
        array.sort((a,b)=>{
               return a["_24hChanges"] - b["_24hChanges"];
            })
    }else if(changeClassName.includes("sort-down-dark")){
         array.sort((a,b)=>{
            return b["_24hChanges"] - a["_24hChanges"];
         })
    }else if(volumeClassName.includes("sort-up-dark")){
        array.sort((a,b)=>{
               return a["_24hVolume"] - b["_24hVolume"];
            })
    }else if(volumeClassName.includes("sort-up-dark")){
         array.sort((a,b)=>{
            return b["_24hVolume"] - a["_24hVolume"];
         })
    }
}



<!-- 疑似要素の変更 -->
    var sortTimes = 0;
    var name;
    var sortObj;
function sort(obj) {
    name = obj.className;

    if(sortObj == obj || sortObj == null){
      if(name.includes("sort-up") && sortTimes == 0){
                obj.classList.remove("sort-up");
                obj.classList.add("sort-up-dark");
                sortTimes = 1;
            }else if(name.includes("sort-up-dark") && sortTimes == 1){
                obj.classList.remove("sort-up-dark");
                obj.classList.add("sort-down-dark");
            }else if(name.includes("sort-down-dark") && sortTimes == 1) {
                obj.classList.remove("sort-down-dark");
                obj.classList.add("sort-up");
                sortTimes = 0;
            }
    }else if(sortObj != obj){
           if(sortObj.className.includes("sort-up-dark")){
                    sortObj.classList.remove("sort-up-dark");
            }else if(sortObj.className.includes("sort-down-dark")){
                    sortObj.classList.remove("sort-down-dark");
            }
            sortObj.classList.add("sort-up");
            obj.classList.remove("sort-up");
            obj.classList.add("sort-up-dark");
    }
    addDataInTable(array_temp);
    sortObj = obj;
}

<!-- ドロップダウンメニューのキープ -->
function keepDropdown() {
     menu_btm.classList.add("show");
     menu_btm.setAttribute("data-bs-toggle","dropdown");
     menu_btm.setAttribute("aria-expanded","true");
     menu.classList.add("show");
     menu.style ="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(0px, 40px);";
     menu.setAttribute("data-popper-placement","bottom-start");
}

<!-- 選択されたコインの絞り -->
function filter(ele) {
     document.getElementById("in").innerHTML = ele.innerHTML;
     menu_btm.classList.remove("show");
     menu_btm.removeAttribute("data-bs-toggle");
     menu_btm.removeAttribute("expanded");
     menu.classList.remove("show");
     menu.removeAttribute("style");
     menu.removeAttribute("data-popper-placement");

     switch(ele.innerHTML.toLowerCase()){
        case "all":
            fileter_option = "";
            break;
        case "usdt":
            fileter_option = "USDT";
            break;
        case "usdc":
            fileter_option = "USDC";
            break;
        case "dai":
            fileter_option = "DAI";
            break;
     }

     addDataInTable(array_temp);
}




<!-- タブ様式変更と選択された取引所のデータ取得 -->
var times;
var bt1;
var bt2;
function tabChg_GetData(ele){
          if(xhr.readyState==4) {

            tableBody.remove();
//                document.getElementById("myTable").innerHTML = "";
            exchange =  ele.innerHTML.toLowerCase();
            if(!ele.className.includes("active")) {
                if(times == null && bt2 == null){
                    document.getElementById("firstTab").classList.remove("active");
                    bt1 = ele;
                    bt1.classList.add("active");
                    times = 1;
                }else if(times == 0){
                    bt2.classList.remove("active");
                    bt1 = ele;
                    bt1.classList.add("active");
                    times = 1;
                }else if(times == 1){
                    bt1.classList.remove("active");
                    bt2 = ele;
                    bt2.classList.add("active");
                    times = 0;
                }
            }
    }
}


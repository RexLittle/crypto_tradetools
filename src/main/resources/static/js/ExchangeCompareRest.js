var exchange1 = "binance";
var exchange2 = "okex";
var str;
var xhr;


//function addDataInTable
var tableBody = document.createElement("tbody");
var tr;
var td;

//table
var menu_btm;
var menu;

//tab bar
var e1;
var e2;


var array_temp;
window.onload=function() {
    e1 = document.getElementById("ex1");
    e2 = document.getElementById("ex2");

    menu_btm = document.getElementById("in");
    menu = document.getElementById("show");


    setInterval(()=>{
        if(window.XMLHttpRequest) {
           xhr = new XMLHttpRequest();
        } else {
           xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var url = "http://localhost:8080/byy/compare/"+exchange1+"&"+exchange2;
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
    sortChoose(array);
    requestAnimationFrame(()=>{
            var jsonName=new Array("pair","exchange1Price","exchange2Price","priceGap","priceGapPercent");
            tableBody.innerHTML="";
            tableBody.id = "myTable";
            table = document.getElementById("tb");
            var fragment = document.createDocumentFragment();
            fragment.appendChild(tableBody);
            if(tableBody.childNodes.length == 0){
            //             str = "";
                 for(i = 0; i < array.length; i++){
                          tr = document.createElement("tr");
                          for(j=1; j<=5; j++){
                              td = document.createElement("td");

                           if(jsonName[j-1] == "priceGap"){
                                  td.innerText = parseFloat(array[i][jsonName[j-1]]).toLocaleString();
                             }
                           else if(jsonName[j-1] == "priceGapPercent"){
                                  td.innerText = parseFloat(array[i][jsonName[j-1]]) + "%";
                             }else{
                                td.innerText = array[i][jsonName[j-1]];
                             }
                              td.classList.add("d-none");
                              td.classList.add("d-lg-table-cell");
                              tr.appendChild(td);
                          }
                        fragment.getElementById("myTable").appendChild(tr);

            //                     str += "<tr>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ pair + "</td>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ lastprice + "</td>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ _24hChanges + "</td>" +
            //                            "<td class= \"d-none d-lg-table-cell\">"+ _24hVolume + "</td>" +
            //                            "<tr>";
                 }
            }
                        table.appendChild(fragment);
                //      document.getElementById("myTable").innerHTML = str;

    });
}

<!-- テーブルソートの選択 -->
function sortChoose(array){
    var pairClassName = document.getElementById("pair").className;
    var tb_ex1ClassName = document.getElementById("tb_ex1").className;
    var tb_ex2ClassName = document.getElementById("tb_ex2").className;
    var priceGapClassName = document.getElementById("priceGap").className;
    var priceGapPercentClassName = document.getElementById("priceGapPercent").className;
    
    if(pairClassName.includes("sort-up-dark")){
        array.sort((a,b)=>{
               return a["pair"].charCodeAt() - b["pair"].charCodeAt();
            })
    }else if(pairClassName.includes("sort-down-dark")){
        array.sort((a,b)=>{
           return b["pair"].charCodeAt() - a["pair"].charCodeAt();
        })
    }else if(tb_ex1ClassName.includes("sort-up-dark")){
        array.sort((a,b)=>{
               return a["exchange1Price"] - b["exchange1Price"];
            })
    }else if(tb_ex1ClassName.includes("sort-down-dark")){
         array.sort((a,b)=>{
            return b["exchange1Price"] - a["exchange1Price"];
         })
    }else if(tb_ex2ClassName.includes("sort-up-dark")){
         array.sort((a,b)=>{
                return a["exchange2Price"] - b["exchange2Price"];
             })
     }else if(tb_ex2ClassName.includes("sort-down-dark")){
           array.sort((a,b)=>{
              return b["exchange2Price"] - a["exchange2Price"];
           })
     }else if(priceGapClassName.includes("sort-up-dark")){
               array.sort((a,b)=>{
                      return a["priceGap"] - b["priceGap"];
                   })
   }else if(priceGapClassName.includes("sort-down-dark")){
               array.sort((a,b)=>{
                  return b["priceGap"] - a["priceGap"];
               })
    } else if(priceGapPercentClassName.includes("sort-up-dark")){
             array.sort((a,b)=>{
                    return a["priceGapPercent"] - b["priceGapPercent"];
                 })
      }else if(priceGapPercentClassName.includes("sort-down-dark")){
             array.sort((a,b)=>{
                return b["priceGapPercent"] - a["priceGapPercent"];
             })
      }else{
           array.sort((a,b)=>{
             return a["pair"].charCodeAt() - b["pair"].charCodeAt();
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



<!-- タブ様式変更と選択された取引所のデータ取得 -->
var tab1_times;
var tab1_bt1;
var tab1_bt2;


function tabChg1(ele){
//          if(xhr.readyState==4) {
//
//            tableBody.remove();
//                document.getElementById("myTable").innerHTML = "";
//            exchange =  ele.innerHTML.toLowerCase();

            if(!ele.className.includes("active")) {
                for(i=0; i<e2.children.length; i++){
                    if(e2.children[i].className.includes("disabled")){
                        e2.children[i].classList.remove("disabled");
                    }else if(e2.children[i].innerHTML == ele.innerHTML) {
                        e2.children[i].classList.add("disabled");
                    }
                }
                if(tab1_times == null && tab1_bt2 == null){
                    document.getElementById("tab1").classList.remove("active");
                    tab1_bt1 = ele;
                    tab1_bt1.classList.add("active");
                    tab1_times = 1;
                }else if(tab1_times == 0){
                    tab1_bt2.classList.remove("active");
                    tab1_bt1 = ele;
                    tab1_bt1.classList.add("active");
                    tab1_times = 1;
                }else if(tab1_times == 1){
                    tab1_bt1.classList.remove("active");
                    tab1_bt2 = ele;
                    tab1_bt2.classList.add("active");
                    tab1_times = 0;
                }
                exchange1 =  ele.innerHTML.toLowerCase();
                document.getElementById("tb_ex1").innerHTML = ele.innerHTML;
            }
//    }



}

var tab2_times;
var tab2_bt1;
var tab2_bt2;
function tabChg2(ele){
//          if(xhr.readyState==4) {
//
//            tableBody.remove();
//                document.getElementById("myTable").innerHTML = "";
//            exchange =  ele.innerHTML.toLowerCase();
            if(!ele.className.includes("active")) {
                for(i=0; i<e1.children.length; i++){
                    if(e1.children[i].className.includes("disabled")){
                        e1.children[i].classList.remove("disabled");
                    }else if(e1.children[i].innerHTML == ele.innerHTML) {
                        e1.children[i].classList.add("disabled");
                    }
                }
                if(tab2_times == null && tab2_bt2 == null){
                    document.getElementById("tab2").classList.remove("active");
                    tab2_bt1 = ele;
                    tab2_bt1.classList.add("active");
                    tab2_times = 1;
                }else if(tab2_times == 0){
                    tab2_bt2.classList.remove("active");
                    tab2_bt1 = ele;
                    tab2_bt1.classList.add("active");
                    tab2_times = 1;
                }else if(tab2_times == 1){
                    tab2_bt1.classList.remove("active");
                    tab2_bt2 = ele;
                    tab2_bt2.classList.add("active");
                    tab2_times = 0;
                }
                exchange2 =  ele.innerHTML.toLowerCase();
                document.getElementById("tb_ex2").innerHTML = ele.innerHTML;
            }
//    }
}


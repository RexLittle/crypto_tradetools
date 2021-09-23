<!-- 獲得したデータの数分の行を作成 -->

window.onload=function() {
//XMLHttpRequestオブジェクトがバックエンドでサーバーとデータのやり取り
        $('.dropdown-toggle').dropdown();
        function ObjData(symbol, exchange1Price, exchange2Price) {
            this.symbol = symbol;
            this.exchange1Price = exchange1Price;
            this.exchange2Price = exchange2Price;
        }

        var xhr;
        var jsonStr;
        var jsonObj;
             if(window.XMLHttpRequest) {
                       xhr = new XMLHttpRequest();
                    }
                    else
                    {
                       xhr=new ActiveXObject("Microsoft.XMLHTTP");
                    }

                    xhr.open("GET","http://localhost:8081/arbitrage",true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                          // readyState == 4リクエスト成功
                                  if(xhr.readyState==4) {
                                      if(xhr.status==200 || xhr.status==304){
                                            var jsonStr = xhr.responseText;
                                            var jsonObj = eval('(' + jsonStr + ')');
                                            var ex1;
                                            var ex2;
                                            var numLength;
                                            var percent;
                                            var difference;
                                                var data = "<tr>" +
                                                            "<td>通貨名</td>" +
                                                            "<td>Binance</td>" +
                                                            "<td>Bithumb</td>" +
                                                            "<td>スプレッド</td>" +
                                                            "<td>パーセンテージ</td>" +
                                                            "</tr>";
                                                 //行数Num指定
                                                 for(var i = 1; i <= jsonObj.length; i++) {
                                                     ex1 = parseFloat(jsonObj[i-1].exchange1Price);
                                                     ex2 = parseFloat(jsonObj[i-1].exchange2Price);

                                                     if(jsonObj[i-1].exchange1Price < jsonObj[i-1].exchange2Price) {
                                                           numLength = ex1.toString().substring(ex1.toString().indexOf('.'),ex1.toString().length).length;
                                                           difference = Math.abs(ex2 - ex1).toFixed(numLength);;
                                                           percent = (difference/ex1 * 100).toFixed(2);
                                                     } else {
                                                           numLength = ex2.toString().substring(ex2.toString().indexOf('.'),ex2.toString().length).length;
                                                           difference = Math.abs(ex1 - ex2).toFixed(numLength);;
                                                           percent = (difference/ex2 * 100).toFixed(2);
                                                     }
                                                     data += "<tr>";
                                                     data += "<td>" + jsonObj[i-1].symbol +"</td>";
                                                     data += "<td>" + ex1 +"</td>";
                                                     data += "<td>" + ex2 +"</td>";
                                                     data += "<td>" + difference + "</td>";


                                                     data += "<td>" + percent +"%</td>";
                                                     data += "</tr>";
                                                 }
                                                 document.getElementById("myTable").innerHTML = data;
                                      }
                    }
        }


}




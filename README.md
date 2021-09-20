# 仮想通貨取引ツール
取引所Binance、Okex、KuCoinのコイン値段の確認と比べが出来るサイトです。
# 経緯
自分が今まで学んだ内容を復習するため、何らかのポートフォリオ作成しょうと思ったら、仮想通貨取引所の価額をまとめたアプリがあればいいと思って、このサイトを作成することになりました。
# サイトURL

# 使用技術
- Java11
  - jackson 2.5.0
  - retrofit 2.5.0
  - Junit 4.12
  - slf4j 1.2.3
  - okhttp 3.14.9
- SpringBoot 2.3.5
  - websocket 1.3.5
- Html、CSS、JavaScript
  - BootStrap5.0.0
- Tomcat

# 機能
- 取引所3社のコイン値段確認
- 取引所3社のコイン値段比べ
# ExchangeApi構造
# 苦労したところ
# 参照先URL

  ExchangeApi参照先:https://github.com/binance-exchange/binance-java-api
  
  Binance:https://binance-docs.github.io/apidocs/spot/en/#change-log
  
  Okex:https://www.okex.com/docs-v5/en/#market-maker-program
  
  KuCoin:https://docs.kucoin.com/#general

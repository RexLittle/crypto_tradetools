# 仮想通貨取引ツール
取引所Binance、Okex、KuCoinのコイン値段の確認と比べが出来るサイトです。
# 経緯
自分が学習するため、何らかのポートフォリオ作成しょうと思ったら、仮想通貨取引所の価額をまとめたアプリがあればいいと思って、このサイトを作成することになりました。
# サイトURL
http://49.135.37.133:2333/crypto_tradetools-0.0.1/ExchangeMarket.html
- ※このサイトはモバイル対応しておりません.
- ※上記のurlを開く際、httpがhttpsに自動的に変更されるため、sの削除する必要があります.
- ※もし上記のサイトが急に反応しなくなる際、1分後このgithubサイトをリフレッシュし、再度上記URLをアクセスしてください。ネット環境の都合で更新されない場合もあり、また後日修復いたします。
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
  ![ExchangeApi構造](https://github.com/RexLittle/crypto_tradetools/blob/main/ExchangeApiClassUml.jpg)
# 工夫したところ
  - 取引所3社兼用のapiを作成するため、FactoryMethod パターンで構造を作成した。

# 今後やっていきたいこと
  - ExchangeApiでwebsocketクライアント複数開く際、Threadの問題で普通にクライアント閉じることが出来なかった。api構造を全体的に見直して、作り直し必要がある
  - Exception対策もっと増やす
  - websiteがサーバーから受信する際、データの量があまりも多く、テーブルがリアルタイムデータ更新するのが難しいので、これも解決する必要がある
# 参照先URL

  ExchangeApi参照先:https://github.com/binance-exchange/binance-java-api
  
  Binance:https://binance-docs.github.io/apidocs/spot/en/#change-log
  
  Okex:https://www.okex.com/docs-v5/en/#market-maker-program
  
  KuCoin:https://docs.kucoin.com/#general

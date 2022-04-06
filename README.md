`KEN_ALL.CSV`をダウンロードして`target`の下に配置する。

- https://www.post.japanpost.jp/zipcode/dl/oogaki-zip.html

Spring Batch用のテーブルを作成する。

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.enabled=false --spring.batch.jdbc.initialize-schema=ALWAYS"
```

MyBatis関連のクラスを生成する。

```bash
./mvnw mybatis-generator:generate
```

CSVファイル(`KEN_ALL.CSV`)を読み込んでDBへ保存するバッチを実行する。

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=fileToDbJob file=target/KEN_ALL.CSV"
```

DBからデータを読み取ってCSVファイルを出力するバッチを実行する。

TODO

DBからデータを読み取ってデータを加工してDBへ保存するバッチを実行する。

TODO


Spring Batch用のテーブルを作成する。

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.enabled=false --spring.batch.jdbc.initialize-schema=ALWAYS"
```

MyBatis関連のクラスを生成する。

```bash
./mvnw mybatis-generator:generate
```

`KEN_ALL.CSV`をダウンロードして配置する。

- https://www.post.japanpost.jp/zipcode/dl/oogaki-zip.html

CSVファイル(`KEN_ALL.CSV`)を読み込んでDBへ保存するバッチを実行する。

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=FileToDb input.file=KEN_ALL.CSV"
```

DBからデータを読み取ってCSVファイルを出力するバッチを実行する。

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=DbToFile output.file=output.csv"
```

DBからデータを読み取ってデータを加工してDBへ保存するバッチを実行する。

TODO

テーブルをクリアするバッチを実行する。

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=OneShot"
```

//package TimeSeriesClustering;
//
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.mllib.clustering.KMeans;
//import org.apache.spark.mllib.clustering.KMeansModel;
//import scala.Tuple2;
// 
//import java.util.Arrays;
// 
///**
// * Spark / MLlib の K-means クラスタリングのデモです。
// *
// * @author KOMIYA Atsushi
// */
//public class Kmeans {
//    public static void main(String[] args) {
//        JavaSparkContext context = new JavaSparkContext("local", "demo");
// 
//        JavaPairRDD&lt;String, double[]> data = context
//                .textFile("iris.txt")
//                .filter(new Function&lt;String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) throws Exception {
//                        // ヘッダ行を取り除きます
//                        return !s.startsWith("\tSepal.Length");
//                    }
//                })
//                .map(new PairFunction&lt;String, String, double[]>() {
//                    @Override
//                    public Tuple2&lt;String, double[]> call(String s) throws Exception {
//                        String[] elems = s.split("\t");
//                        double[] values = new double[elems.length - 2];
// 
//                        int last = elems.length - 1;
//                        for (int i = 1; i &lt; last; i++) {
//                            values[i - 1] = Double.parseDouble(elems[i]);
//                        }
// 
//                        return new Tuple2&lt;String, double[]>(elems[last], values);
//                    }
//                });
// 
//        // クラスタの個数を指定します
//        final int k = 3;
// 
//        // K-means のイテレーション最大回数を指定します
//        final int maxIterations = 100;
// 
//        final KMeansModel clusters = KMeans.train(
//                data.map(new Function&lt;Tuple2&lt;String, double[]>, double[]>() {
//                    @Override
//                    public double[] call(Tuple2&lt;String, double[]> tuple) throws Exception {
//                        return tuple._2();
//                    }
//                }).rdd(),
//                k,
//                maxIterations);
// 
//        // 各クラスタの中心を確認する
//        System.out.println("## クラスタの中心");
//        for (double[] center : clusters.clusterCenters()) {
//            System.out.println(Arrays.toString(center));
//        }
// 
//        // 各データがどのクラスタに分類されたのかを確認する
//        System.out.println("## 各データのクラスタリング結果");
//        data.foreach(new VoidFunction&lt;Tuple2&lt;String, double[]>>() {
//            @Override
//            public void call(Tuple2&lt;String, double[]> tuple) throws Exception {
//                int result = clusters.predict(tuple._2());
//                System.out.printf("%s (%s) : cluster = %d",
//                        Arrays.toString(tuple._2()),
//                        tuple._1(),
//                        result);
//                System.out.println();
//            }
//        });
//    }
//}
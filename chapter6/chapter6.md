# 文本分类和文档分类 Classifying Texts and Documents

在这一章节，我们将会展示如何使用不同的NLP工具的API(应用程序接口)来实现文本分类。这个工作不应与文本聚类(text clustering)混淆。聚类关注的是在没有预定义类别的场景下完成文本的识别。而文本分类则是与此相反，会提供预定义的类别。我们关注的文本分类是包含标签信息的，这些标签指定了文本的类型。

文本分类的基本上都是先通过训练模型开始，训练得到的模型要经过验证，然后才能用于文档的分类。我们主要关注训练和使用的过程。

文档可以基于任意多钟的属性来分类，例如文档的主题、文档的类型、发表时间、作者、使用的语言、以及适用的阅读水平等。有一些分类方法需要人工标记样本数据的介入。

情感分析(Sentiment Analysis)，又称倾向性分析，意见抽取(Option extraction)，也是一种分类任务。它关注的是一份文档想要向读者传递什么，是正面的还是负面的？我们也会讨论相关的一些技术来实现这种分析。

## 分类的应用

文本分类可以用于许多不同的目的：

* 垃圾邮件检测 (Spam detection)
* 作者身份识别 (Authorship attribution)
* 情感分析 (Sentiment analysis)
* 年龄、性别的识别 (Age and gender identification)
* 文档主题识别 (Determing the subject of a document)
* 语种识别 (Language identification)

对于大部分的电子邮件用户而言，垃圾邮件都是一个不幸的事实。如果一封邮件可以被分类成垃圾邮件，那么它就可以被放置在一个专门的垃圾邮件文件夹中。邮件文本的信息分析的过程中，某些属性可以被用于判定邮件是否是垃圾邮件。这些属性包括了~~拼写错误~~、~~缺乏收件人的恰当格式的电子邮件地址~~，以及~~非标准的URL~~。

分类也可被用于识别文档的作者身份。曾经有人通过已有的如《联邦党人文集》(The Federalist Papers)这样的作品，来识别出小说《原色》(Primary Colors)的作者真实身份。

情感分析是一种判定文本中态度和观点的技术。影评是一种流行的方式，这种方法也可以应用在几乎所有的产品评价中。这可以帮助生产方更好的评估他们的产品在用户心中是怎样的感受。通常，文本被分类成消极或积极的类别。情感分析也被称为意见抽取(option extraction)、意见挖掘(option mining)和主观分析(subjectivity analysis)。典型的例子如，消费者对于股票市场的信心和交易可以通过微博和其他来源数据预测得到。

识别文档的主题，对于管理大量文档时是有帮助的。搜索引擎是非常关注这类技术的，但也会借鉴如标签云(tag cloud)的方式进行文档的简单归类。
标签云展现的一组词语，体现了这些词语出现的相对频率大小。

下面这幅图就是由[IBM Word Cloud Generator](http://www.softpedia.com/get/Office-tools/Other-Office-Tools/IBM-Word-Cloud-Generator.shtml)
(<http://www.softpedia.com/get/Office-tools/Other-Office-Tools/IBM-Word-Cloud-Generator.shtml>)工具生成的，这幅图的出处是<https://upload.wikimedia.org/wikipedia/commons/9/9e/Foundation-l_word_cloud_without_headers_and_quotes.png>。

![Word Cloud of Wikipedia](img/Foundation-l_word_cloud_without_headers_and_quotes.png)

一段文字的语种识别也用到了分类技术。大量的NLP问题中，我们都需要针对不同的语言设计不同的模型，因此这种识别技术也是非常重要和常用的。

## 理解情感分析

关于情感分析，我们关心的是对于一个特定产品或话题，什么人持有什么样的观点。这可以帮助我们了解一个城市的居民对于某支球队表现的态度，是积极还是负面的情感。有趣的是，人们可能对于球队表现和球队管理团队的态度可能是截然相反的。

针对一种产品，若能够自动分析出人们对于产品不同方面、属性的态度，并以有意义的方式呈现出来，将会是有益的。下面一幅图，举例说明了从凯利蓝皮书(Kelly Blue Book,是美国著名的第三方独立的车友网站)网友调查得到的，关于丰田凯美瑞2014款车型的评价。

![2014-toyota-camry-expert-review](img/2014-toyota-camry-expert-review.png)

我们可以看到，如车的总体评分(Overall Rating)、价格(Value)，都以条形图和数值的方式展现。而这些信息的获取和计算都可以通过情感分析来自动完成。

情感分析可以应用在句子中、从句中，还可以用于整篇文档。情感分析的结果不仅可以是表示积极或负面，还可以是以数值大小的评分，例如1到10之间的区间。More complex attitude types are possible.Further complicating the process, within a single sentence or document, different
sentiments could be expressed against different topics.

我们怎么知道不同的词语具有怎样的情感类型呢？答案就是通过情感词典(sentiment lexicons)，这个词典包含了不同词语在情感方面的含义。General Inquirer(<http://www.wjh.harvard.edu/~inquirer/>) 就是这样一种开源词典，其中包括了有1915个代表积极的此物，它还包括了一些如痛苦、喜悦、激烈和鼓动等方面的词语。MPQA Subjectivity Cues Lexicon(<http://mpqa.cs.pitt.edu/>)也是一种开源词库。

有时候我们可能希望建立一个词典，这通常可以通过半监督学习的方式，借鉴少量标记的样本或规则来引导整个词典的建立。
当我们面临的问题没有很适合的词典时，这种方法是特别有用的。

我们不仅仅关注情感是积极的还是负面的，我们对于确定情感的属性（有时候称为情感目标）同样感兴趣。参考下面的例子：

>"The ride was very rough but the attendants did an excellent job of making us comfortable."
虽然我们乘坐的交通工具非常简陋，但是上面的服务员提供的超乎想像的服务令我们感到非常舒适。

这句话包含了两种感受：简陋和舒适。前者是负面的，后者是正面的。这些情感或感受的属性或称为目标，分别来自于交通工具和服务。

## 文本分类技术

分类关心给定的一份文档是否与一组不同的文档相匹配。分类文本有两种方式：
> Classification is concerned with taking a specific document and determining if it fits into one of several other document groups. 

* 基于规则(Rule-based)的分类
* 基于有监督机器学习(Supervised Machine Learning, SML)的分类

第一种方法使用的规则由词语和多种属性组合而成，这些规则是由领域专家精心构建的。这种方式的分类效果非常有效，
但是创建一组合适的规则是非常消耗时间的。

有监督机器学习使用一组有主街道文档作为训练集，来创建出用于分类的模型。这些模型我们称为分类器(Classifier)。目前有非常多的不同的机器学习算法，
如朴素贝叶斯(Naive Bayes)、支持向量机(Support-Vector Machine, SVM)和K近邻(K-nearest neighbor)等。

已经有数不清的文献资料论述了这些算法和技术，我们在此就不讨论这些算法的工作原理了，有兴趣的读者可以自行查阅相关资料。

## 文本分类API使用实战( Using APIs to classify text)

我们选用了 OpenNLP、Stanford API 和LingPipe三种文本分类库来演示。因为 LingPipe 提供了一些不同的分类方法，
我们将会在其中花费多一些篇幅。

### 使用 OpenNLP (Using OpenNLP)

对于OpenNLP，我们主要是使用```DocumentCategorizer```这一接口完成分类工作。这个接口实现了```DocumentCategorizerME```
这个类是通过最大熵来实现将文本分类到预定义的类别中。我们将：

* 演示如何训练分类器模型
* 展示如何使用训练得到的模型进行分类


#### 训练一个OpenNLP的分类模型(Training an OpenNLP classification model)

因为OpenNLP中没有内置模型，我们需要为分类任务训练一个自己的模型。
这个过程包括，创建一个包含训练数据集的文件，然后用`DocumentCategorizerME`进行模型训练。训练得到的模型通常是以文件形式保存，以便后续使用。

训练数据集文件是由一行行文本构成的，每一行代表了一个训练数据样本。
样本的第一个单词表示样本所属类别，在类别后面紧跟一个空格，然后是一段文本。
例如下面是一条关于dog类别的样本：

``` dog The most interesting feature of a dog is its ... ```

为了演示训练过程，我们创建了一个名为`en-animals.train`的文件，里面包括了两种类别：
cat和dog。具体的训练样本，我们使用的是来自Wikipedia的内容。
dog样本使用的是(<https://en.wikipedia.org/wiki/Dog#As_pets>)中狗作为宠物的章节。
cat样本选用了(<https://en.wikipedia.org/wiki/Human_interaction_with_cats#>)作为宠物章节和驯化品种首段内容。我们去掉了文献中的引用标记。

训练数据集文件的每一行的如下（译者注：限于篇幅，仅列出每个样本的前面少量内容）：

```
    dog The most widespread form of interspecies bonding occurs ...
    dog There have been two major trends in the changing status of ...
    dog There are a vast range of commodity forms available to ...
    dog An Australian Cattle Dog in reindeer antlers sits on Santa's lap ...
    dog A pet dog taking part in Christmas traditions ...
    dog The majority of contemporary people with dogs describe their ...
    dog Another study of dogs' roles in families showed many dogs have ...
    dog According to statistics published by the American Pet Products ...
    dog The latest study using Magnetic resonance imaging (MRI) ...
    cat Cats are common pets in Europe and North America, and their ...
    cat Although cat ownership has commonly been associated ...
    cat The concept of a cat breed appeared in Britain during ...
    cat Cats come in a variety of colors and patterns. These are physical ...
    cat A natural behavior in cats is to hook their front claws periodically ...
    cat Although scratching can serve cats to keep their claws from growing ...
```

在构建训练数据集时，应当保证有足够多的样本。我们这里用到的数据集规模对于一些分析是不够的。
但是，后面可以看到，对于正确分类dog和cat是已经足够了。

`DoccatModel`这个类用于程序化表达训练得到的文本分类模型。模型可以通过`train`方法将标注文本加以训练得到。 
训练是还需要提供一个字符串标记文本的语种，用`ObjectStream<DocumentSample>`来实现序列读训练数据。 
`DocumentSample`则代码了一条文本样本，包括了样本的类别标记信息和数据信息(文本句子)。

在下面的例子中，前面准备得到的`en-animal.train`文件将用于训练得到我们的分类器模型。
训练数据集文件是由普通文本内容构成，所以这里我们使用`PlainTextByLineStream`来解析
输入的样本，并转换成特定的样本对象数据流`ObjectStream<DocumentSample>`，然后调用
`DocumentCategorizerME`的`train`方法来生成模型。我们最后使用一个输出流将模型持久化
存储到本地磁盘上，以便后续重复使用。

(** 原文的代码是有错误的，try-with-resources block是不符合java语法的，
为了避免更多的误解和解释，我用正确语法重写如下**)

```Java
    public static void  train() throws IOException {
        DoccatModel model = null;
        ObjectStream<String> lineStream =
                new PlainTextByLineStream(new MarkableFileInputStreamFactory(
                        new File("en-animal.train")), "UTF-8");
        ObjectStream<DocumentSample> sampleStream =
                new DocumentSampleStream(lineStream);

        TrainingParameters param = TrainingParameters.defaultParams();
        DoccatFactory factory = new DoccatFactory();
        model = DocumentCategorizerME.train("en", sampleStream,param,factory);

        model.serialize(new FileOutputStream("en-animal.model"));
    }
```

![output-for-OpenNLP](img/output-for-OpenNLP.png)

模型最后通过`serialize`方法保存到本地磁盘上。

####应用`DocumentCategorizerME`进行文本分类

模型建立后，我们就可以用`DocumentCategorizerME`这个类进行文本分类。
首先读入模型，然后创建一个`DocumentCategorizerME`类的实例，这个实例是个分类器。
然后可以调用分类器的`categorize`方法来计算获得验证文本属于每种类型的可能性大小。

由于我们是从文件中读取模型，所以相关的`IOException`需要处理一下，
读入模型的代码大致如下：
```Java
    try {
        InputStream modelIn =
            new FileInputStream(new File("en-animal.model"));
        ...
    } catch (IOException ex) {
        // Handle exceptions
    }
```
通过输入流，我们创建一个`DoccatModel`模型实例，并用这个模型实例来创建一个`DocumentCategorizerME`的分类器，过程如下：

```Java
    DoccatModel model = new DoccatModel(modelIn);
    DocumentCategorizerME categorizer =
        new DocumentCategorizerME(model);
```
分类器的`categorize`方法将输入文本进行分类，并返回一个浮点数数组。
数组中的每一个浮点数代表了输入文本属于某一具体类别的可能性。
`DocumentCategorizerME`的`getNumberOfCategories`方法返回模型中的类别数量。
`getCategory`方法则返回输入index对应的类别名称。

下面的代码展示了如何获得输入文本在各种类别上的可能性，并展示了如何输出类别和
可能性的对应关系。
```Java
    double[] outcomes = categorizer.categorize(inputText);
    for (int i = 0; i < categorizer.getNumberOfCategories(); i++) {
        String category = categorizer.getCategory(i);
        System.out.println(category + " - " + outcomes[i]);
    }
```

为了演示分类效果，
我们选取了Wikipedia上的关于通话故事《绿野仙踪》中桃乐茜的小狗Toto
 ( <http://en.wikipedia.org/wiki/Toto_%28Oz%29>) 的部分章节
作为测试样本，这里选取的文章的`The classic books`章节的第一句。
```Java
    String toto = "Toto belongs to Dorothy Gale, the heroine of "
        + "the first and many subsequent books. In the first "
        + "book, he never spoke, although other animals, native "
        + "to Oz, did. In subsequent books, other animals "
        + "gained the ability to speak upon reaching Oz or "
        + "similar lands, but Toto remained speechless.";
```

关于猫的样本，我们选用了Wikipedia中描述玳瑁斑纹的章节(< http://en.wikipedia.org/wiki/Cats_and_humans>):

```Java
    String calico = "This cat is also known as a calimanco cat or "
        + "clouded tiger cat, and by the abbreviation 'tortie'. "
        + "In the cat fancy, a tortoiseshell cat is patched "
        + "over with red (or its dilute form, cream) and black "
        + "(or its dilute blue) mottled throughout the coat.";
```
用`Toto`作为测试样本，我们获得如下输出，输出表明输入文本描述的应该是属于狗这一类：
```
dog - 0.5870711529777994
cat - 0.41292884702220056
```
而`calico`的分类结果是
```
dog - 0.28960436044424276
cat - 0.7103956395557574
```

我们可以用`getBestCategory`方法直接获得最匹配的那一个分类，其输入是`outcomes`
数组。而`getAllResults`则会返回每种类别极其对应的可能性。
示例代码如下：
```Java
    System.out.println(categorizer.getBestCategory(outcomes));
    System.out.println(categorizer.getAllResults(outcomes));
```
关于`calico`的输出则是：
```
cat
dog[0.2896] cat[0.7104]
```

### 使用斯坦福API(Using Stanford API)

斯坦福API提供了若干种分类器。
我们将分别试用`ColumnDataClassifier`类和`StanfordCoreNLP`管线(pipeline)
进行文本分类和情感分析。斯坦福API提供的分类器有时会比较难使用。在这里，
我们将以归类箱子大小为例来展示如何使用`ColumnDataClassifier`。 
对于管线，我们将示例如何对短语进行正向或负向情感的判定。
该工具可以从链接<http://nlp.stanford.edu/software/classifier.shtml>上下载得到。

#### 使用`ColumnDataClassifier`类进行分类(Using the ColumnDataClassifier class for classification)

`ColumnDataClassifier`分类器面对的每一条样本都可以是由多个数值构成的。在本章示例中，
我们将用一个训练集文件来创建一个分类器。然后用一个测试集文件来验证分类器的性能。
`ColumnDataClassifier`类是通过一个属性文件来控制创建过程的。

这里我们创建的分类器将通过输入的箱子三维属性值来归类。
总共有三种类别，分别是：小、中、大。
一个箱子的长宽高三个纬度分别用浮点数来表示，用于刻画箱子的特征。

属性文件列举了相关的参数信息，并提供了训练数据和测试数据文件。
属性文件可以有非常多的参数(译者注：可以从`ColumnDataClassifier`的Javadoc中获得
所有支持的属性参数)，这里我们只列出了本例子用到的参数。

我们使用的属性文件叫做`box.prop`。最前面的4个属性指明了训练数据和测试数据中
特征的数量。因为我们使用到三维数据，所以需要指明三列实数属性(realValued)。
另外的`trainFile`和`testFile`属性则是指明了相应训练数据和测试数据文件的名字和路径。
完整的示例如下：

```
    useClassFeature=true
    1.realValued=true
    2.realValued=true
    3.realValued=true
    trainFile=.box.train
    testFile=.box.test
```

训练数据和测试数据文件拥有相同的内容格式。每一行都包括了类别和三个实数，
数据之间以`tab`键相隔。我们使用的`box.train`包括60个样本，而`box.train`中
包括了30个样本。这些文件可以从出版社网站<www.packtpub.com>上下载得到。
以`box.train`的第一行内容为例，表明箱子的类别是“小”，其高度、宽度、长度分别是
2.34，1.60和1.50，对应在文件中是:
```
    small 2.34 1.60 1.50
```

创建分类器的代码示例如下。用属性文件作为构造函数的参数来创建
一个`ColumnDataClassifier`分类器的实例。然后调用`makeClassifier`方法
来返回一个`Classifier`接口实例。这个接口支持三种方法，这里将会展示其中的两种。
`readTrainingExamples`方法则是实现从训练数据集文件中读取训练数据样本。

```Java
    ColumnDataClassifier cdc =
        new ColumnDataClassifier("box.prop");
    Classifier<String, String> classifier =
        cdc.makeClassifier(cdc.readTrainingExamples("box.train"));
```

编译执行后，将会得到如下输出。输出包括了多种信息，第一部分
是重复输出属性文件内容，可用于校验属性文件是否正确。

```Ouput
3.realValued = true
testFile = .box.test
...
trainFile = .box.train
```

紧接着是读取的数据集的各类特征信息：

```
Reading dataset from box.train ... done [0.1s, 60 items].
numDatums: 60
numLabels: 3 [small, medium, large]
...
AVEIMPROVE The average improvement / current value
EVALSCORE The last available eval score
Iter ## evals ## <SCALING> [LINESEARCH] VALUE TIME |GNORM| {RELNORM}
AVEIMPROVE EVALSCORE
```

接着就是利用样本数据迭代计算得到分类器的过程：

```
Iter 1 evals 1 <D> [113M 3.107E-4] 5.985E1 0.00s |3.829E1| {1.959E-1}
0.000E0 -
Iter 2 evals 5 <D> [M 1.000E0] 5.949E1 0.01s |1.862E1| {9.525E-2} 3.058E-
3 -
Iter 3 evals 6 <D> [M 1.000E0] 5.923E1 0.01s |1.741E1| {8.904E-2} 3.485E-
3 -
...
Iter 21 evals 24 <D> [1M 2.850E-1] 3.306E1 0.02s |4.149E-1| {2.122E-3}
1.775E-4 -
Iter 22 evals 26 <D> [M 1.000E0] 3.306E1 0.02s
QNMinimizer terminated due to average improvement: | newest_val -
previous_val | / |newestVal| < TOL
Total time spent in optimization: 0.07s
```

此时此刻，分类器则就绪待用了。接着则用测试数据文件来验证分类器。
这里是用`ObjectBank`的`getLineIterator`方法来获得测试数据文件的每一行样本。
其中完成了数据转换。这个循环处理过程如下：

```Java
    for (String line :
        ObjectBank.getLineIterator("box.test", "utf-8")) {
        ...
    }
```

在`for-each`内部，通过`makeDatumFromLine`方法将一行输入转换成一个`Datum`实例。
然后用分类器的`classOf`方法来获得输入样本的预测结果类型。

```Java
    Datum<String, String> datum = cdc.makeDatumFromLine(line);
    System.out.println("Datum: {"
        + line + "]\tPredicted Category: "
        + classifier.classOf(datum));
```
上面的代码运行后，测试数据的每一行都会被处理，并打印出预测的结果。
这里摘录了头两行和最后两行结果作为示意。
```
Datum: {small 1.33 3.50 5.43] Predicted Category: medium
Datum: {small 1.18 1.73 3.14] Predicted Category: small
...
Datum: {large 6.01 9.35 16.64] Predicted Category: large
Datum: {large 6.76 9.66 15.44] Predicted Category: large
```

我们也可以单独对一个具体输入做分类，需要调用的是`makeDatumFromStrings`
方法来创建`Datum`实例，后续则是一样的流程。在下面的代码中，我们构建了一个
string数组来描述一个样本，第一个字符串表示的样本的类别，
这里是待预测的结果，所以可以置为空串，后续的三个字符串则与前面叙述的箱子三维信息一致。

```Java
    String sample[] = {"", "6.90", "9.8", "15.69"};
    Datum<String, String> datum =
        cdc.makeDatumFromStrings(sample);
    System.out.println("Category: " + classifier.classOf(datum));
```
这个代码的输出是箱子的预测类别：
```
Category: large
```

#### 利用斯坦福管线进行情感分析(Using the Stanford pipeline to perform sentiment analysis)

本节描述如何使用斯坦福API进行情感分析。API中的`StanfordCoreNLP`库提供了管线(`pipeline`)来帮助文本分析。

我们选取了电影《阿甘正传》(Forrest Gump)的三条影评内容进行实验，数据来源是`Rotten Tomatoes`(< http://www.rottentomatoes.com/m/forrest_gump/ >)：

```Java
    String review = "An overly sentimental film with a somewhat "
        + "problematic message, but its sweetness and charm "
        + "are occasionally enough to approximate true depth "
        + "and grace. ";

    String sam = "Sam was an odd sort of fellow. Not prone "
        + "to angry and not prone to merriment. Overall, "
        + "an odd fellow.";

    String mary = "Mary thought that custard pie was the "
        + "best pie in the world. However, she loathed "
        + "chocolate pie.";
```


To perform this analysis, we need to use a sentiment  annotator as shown here.
This also requires the use of the  tokenize ,  ssplit and  parse annotators. The
parse annotator provides more structural information about the text, which will
be discussed in more detail in Chapter 7, Using a Parser to Extract Relationships:

```Java
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, parse, sentiment");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
```

The text is used to create an  Annotation instance, which is then used as the
argument to the  annotate method that performs the actual work, as shown here:


```Java
    Annotation annotation = new Annotation(review);
    pipeline.annotate(annotation);
```

```Java
    String[] sentimentText = {"Very Negative", "Negative",
        "Neutral", "Positive", "Very Positive"};
```

```Java
    for (CoreMap sentence : annotation.get(
        CoreAnnotations.SentencesAnnotation.class)) {
        Tree tree = sentence.get(
        SentimentCoreAnnotations.AnnotatedTree.class);
        int score = RNNCoreAnnotations.getPredictedClass(tree);
        System.out.println(sentimentText[score]);
    }
```




### Using LingPipe to classify text

We will use LingPipe to demonstrate a number of classification tasks including
general text classification using trained models, sentiment analysis, and language
identification. We will cover the following classification topics:
•  Training text using the  Classified class
•  Training models using other training categories
•  How to classify text using LingPipe
•  Performing sentiment analysis using LingPipe
•  Identifying the language used
Several of the tasks described in this section will use the following declarations.
LingPipe comes with training data for several categories. The  categories array
contains the names of the categories packaged with LingPipe:


```Java
    String[] categories = {"soc.religion.christian",
        "talk.religion.misc","alt.atheism","misc.forsale"};
```
The  DynamicLMClassifier class is used to perform the actual classification.
It is created using the  categories array giving it the names of the categories to use.
The  nGramSize value specifies the number of contiguous items in a sequence used in
the model for classification purposes:


```Java
    int nGramSize = 6;
    DynamicLMClassifier<NGramProcessLM> classifier =
        DynamicLMClassifier.createNGramProcess(
            categories, nGramSize);
```

#### Training text using the Classified class

General text classification using LingPipe involves training the  DynamicLMClassifier
class using training files and then using the class to perform the actual classification.
LingPipe comes with several training datasets as found in the LingPipe directory,
demos/data/fourNewsGroups/4news-train . We will use these to illustrate the
training process. This example is a simplified version of the process found at
http://alias-i.com/lingpipe/demos/tutorial/classify/read-me.html .

We start by declaring the training directory:
```Java
    String directory = ".../demos";
    File trainingDirectory = new File(directory
        + "/data/fourNewsGroups/4news-train");
```

In the training directory, there are four subdirectories whose names are listed in
the  categories array. In each subdirectory is a series of files with numeric names.
These files contain newsgroups ( http://qwone.com/~jason/20Newsgroups/ ) data
that deal with that directories, names.

The process of training the model involves using each file and category with the
DynamicLMClassifier class'  handle method. The method will use the file to create
a training instance for the category and then augment the model with this instance.
The process uses nested for-loops.

The outer for-loop creates a  File object using the directory's name and then
applies the  list method against it. The  list method returns a list of the files in
the directory. The names of these files are stored in the  trainingFiles array,
which will be used in the inner loop:

```Java
    for (int i = 0; i < categories.length; ++i) {
        File classDir =
            new File(trainingDirectory, categories[i]);
        String[] trainingFiles = classDir.list();
        // Inner for-loop
    }
```

The inner for-loop, as shown next, will open each file and read the text from the file.
The  Classification class represents a classification with a specified category. It is
used with the text to create a  Classified instance. The  DynamicLMClassifier class'
handle method updates the model with the new information:

```Java
    for (int j = 0; j < trainingFiles.length; ++j) {
        try {
            File file = new File(classDir, trainingFiles[j]);
            String text = Files.readFromFile(file, "ISO-8859-1");
            Classification classification =
                new Classification(categories[i]);
            Classified<CharSequence> classified =
                new Classified<>(text, classification);
            classifier.handle(classified);
        } catch (IOException ex) {
            // Handle exceptions
        }
    }
```

Notice：You can alternately use the com.aliasi.util.Files class
instead in java.io.File, otherwise the readFromFile
method will not be available.

The classifier can be serialized for later use as shown here. The
AbstractExternalizable class is a utility class that supports the serialization of
objects. It has a static  compileTo method that accepts a  Compilable instance and a
File object. It writes the object to the file, as follows:

```Java
    try {
        AbstractExternalizable.compileTo( (Compilable) classifier,
            new File("classifier.model"));
    } catch (IOException ex) {
        // Handle exceptions
    }
```

The loading of the classifier will be illustrated in the Classifying text using LingPipe
section later in this chapter.

#### Using other training categories

Other newsgroups data can be found at  http://qwone.com/~jason/20Newsgroups/ .
These collections of data can be used to train other models as listed in the following
table. Although there are only 20 categories, they can be useful training models.
Three different downloads are available where some have been sorted and in others,
duplicate data has been removed:


+---------------------------+-----------------------+
| Newsgroups                |                       |
+===========================+=======================+
|comp.graphics              | sci.crypt             | 
+---------------------------+-----------------------+
|comp.os.ms-windows.misc    |sci.electronics        | 
+---------------------------+-----------------------+
|comp.sys.ibm.pc.hardware   |sci.med                | 
+---------------------------+-----------------------+
|comp.sys.mac.hardware      |sci.space              | 
+---------------------------+-----------------------+
|comp.windows.x             |misc.forsale           | 
+---------------------------+-----------------------+
|rec.autos                  |talk.politics.misc     | 
+---------------------------+-----------------------+
|rec.motorcycles            |talk.politics.guns     |
+---------------------------+-----------------------+
|rec.sport.baseball         |talk.politics.mideast  |
+---------------------------+-----------------------+
|rec.sport.hockey           |talk.religion.misc     |
+---------------------------+-----------------------+
|alt.atheism                |                       |
+---------------------------+-----------------------+



#### Classifying text using LingPipe

To classify text, we will use the  DynamicLMClassifier class'  classify method.
We will demonstrate its use with two different text sequences:
•  forSale : The first is from  http://www.homes.com/for-sale/ where we use
the first complete sentence
•  martinLuther : The second is from  http://en.wikipedia.org/wiki/
Martin_Luther where we use the first sentence of the second paragraph
These strings are declared here:

```Java
    String forSale =
        "Finding a home for sale has never been "
        + "easier. With Homes.com, you can search new "
        + "homes, foreclosures, multi-family homes, "
        + "as well as condos and townhouses for sale. "
        + "You can even search our real estate agent "
        + "directory to work with a professional "
        + "Realtor and find your perfect home.";

    String martinLuther =
        "Luther taught that salvation and subsequently "
        + "eternity in heaven is not earned by good deeds "
        + "but is received only as a free gift of God's "
        + "grace through faith in Jesus Christ as redeemer "
        + "from sin and subsequently eternity in Hell.";
```
To reuse the classifier serialized in the previous section, use the
AbstractExternalizable class'  readObject method as shown here. We will use the
LMClassifier class instead of the  DynamicLMClassifier class. They both support the
classify method but the  DynamicLMClassifier class is not readily serializable:


```Java
    LMClassifier classifier = null;
    try {
        classifier = (LMClassifier)
        AbstractExternalizable.readObject(
        new File("classifier.model"));
    } catch (IOException | ClassNotFoundException ex) {
        // Handle exceptions
    }
```

In the next code sequence, we apply the  LMClassifier class'  classify method.
This returns a  JointClassification instance, which we use to determine the
best match:

```Java
    JointClassification classification =
    classifier.classify(text);
    System.out.println("Text: " + text);
    String bestCategory = classification.bestCategory();
    System.out.println("Best Category: " + bestCategory);
```

For the  forSale text, we get the following output:

```
Text: Finding a home for sale has never been easier. With Homes.com,
you can search new homes, foreclosures, multi-family homes, as well as
condos and townhouses for sale. You can even search our real estate agent
directory to work with a professional Realtor and find your perfect home.
Best Category: misc.forsale
```

For the  martinLuther text, we get the following output:
```
Text: Luther taught that salvation and subsequently eternity in heaven
is not earned by good deeds but is received only as a free gift of God's
grace through faith in Jesus Christ as redeemer from sin and subsequently
eternity in Hell.
Best Category: soc.religion.christian
```

They both correctly classified the text.

#### Sentiment analysis using LingPipe

Sentiment analysis is performed in a very similar manner to that of general text
classification. One difference is the use of only two categories: positive and negative.

We need to use data files to train our model. We will use a simplified version of
the sentiment analysis performed at  http://alias-i.com/lingpipe/demos/
tutorial/sentiment/read-me.html using sentiment data found developed
for movies ( http://www.cs.cornell.edu/people/pabo/movie-review-data/
review_polarity.tar.gz ). This data was developed from 1,000 positive and 1,000
negative reviews of movies found in IMDb's movie archives.

These reviews need to be downloaded and extracted. A  txt_sentoken directory
will be extracted along with its two subdirectories:  neg and  pos . Both of these
subdirectories contain movie reviews. Although some of these files can be held
in reserve to evaluate the model created, we will use all of them to simplify
the explanation.

We will start with re-initialization of variables declared in the Using LingPipe to
classify text section. The  categories array is set to a two-element array to hold the
two categories. The  classifier variable is assigned a new  DynamicLMClassifier
instance using the new category array and  nGramSize of size 8:

```Java
    categories = new String[2];
    categories[0] = "neg";
    categories[1] = "pos";
    nGramSize = 8;
    classifier = DynamicLMClassifier.createNGramProcess(
        categories, nGramSize);
```

As we did earlier, we will create a series of instances based on the contents found
in the training files. We will not detail the following code as it is very similar to that
found in the Training text using the Classified class section. The main difference is there
are only two categories to process:

```Java
    String directory = "...";
    File trainingDirectory = new File(directory, "txt_sentoken");
    for (int i = 0; i < categories.length; ++i) {
        Classification classification =
        new Classification(categories[i]);
        File file = new File(trainingDirectory, categories[i]);
        File[] trainingFiles = file.listFiles();
        for (int j = 0; j < trainingFiles.length; ++j) {
            try {
                String review = Files.readFromFile(
                    trainingFiles[j], "ISO-8859-1");
                Classified<CharSequence> classified =
                    new Classified<>(review, classification);
                classifier.handle(classified);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
```

The model is now ready to be used. We will use the review for the movie
Forrest Gump:

```Java
    String review = "An overly sentimental film with a somewhat "
        + "problematic message, but its sweetness and charm "
        + "are occasionally enough to approximate true depth "
        + "and grace. ";
```

We use the  classify method to perform the actual work. It returns a
Classification instance whose  bestCategory method returns the best category,
as shown here:

```Java
    Classification classification = classifier.classify(review);
    String bestCategory = classification.bestCategory();
    System.out.println("Best Category: " + bestCategory);
```
When executed, we get the following output:
```
Best Category: pos
```
This approach will also work well for other categories of text.

#### Language identification using LingPipe

LingPipe comes with a model,  langid-leipzig.classifier , trained for several
languages and is found in the  demos/models directory. A list of supported languages
is found in the following table. This model was developed using training data derived
from the Leipzig Corpora Collection ( http://corpora.uni-leipzig.de/ ). Another
good tool can be found at  http://code.google.com/p/language-detection/ 

![language-detection](img/language-detection.png)

To use this model, we use essentially the same code we used in the Classifying text
using LingPipe section earlier in this chapter. We start with the same movie review
of Forrest Gump:

```Java
    String text = "An overly sentimental film with a somewhat "
        + "problematic message, but its sweetness and charm "
        + "are occasionally enough to approximate true depth "
        + "and grace. ";
    System.out.println("Text: " + text);
```

The  LMClassifier instance is created using the  langid-leipzig.classifier file:

```Java
    LMClassifier classifier = null;
    try {
        classifier = (LMClassifier)
        AbstractExternalizable.readObject(
        new File(".../langid-leipzig.classifier"));
    } catch (IOException | ClassNotFoundException ex) {
        // Handle exceptions
    }
```

The  classify method is used followed by the application of the  bestCategory
method to obtain the best language fit, as shown here:

```Java
    Classification classification = classifier.classify(text);
    String bestCategory = classification.bestCategory();
    System.out.println("Best Language: " + bestCategory);
```

The output is as follows with English being chosen:

```
Text: An overly sentimental film with a somewhat problematic message, but
its sweetness and charm are occasionally enough to approximate true depth
and grace.
Best Language: en
```

The following code example uses the first sentence of the Swedish Wikipedia entry
in Swedish ( http://sv.wikipedia.org/wiki/Svenska ) for the text:

```Java
    text = "Svenska är ett östnordiskt språk som talas av cirka "
        + "tio miljoner personer[1], främst i Finland "
        + "och Sverige.";
```

The output, as shown here, correctly selects the Swedish language:

```
Text: Svenska är ett östnordiskt språk som talas av cirka tio miljoner
personer[1], främst i Finland och Sverige.
Best Language: se
```
Training can be conducted in the same way as done for the previous LingPipe
models. Another consideration when performing language identification is that
the text may be written in multiple languages. This can complicate the language
detection process.

## Summary

In this chapter, we discussed the issues surrounding the classification of text and
examined several approaches to perform this process. The classification of text is
useful for many activities such as detecting e-mail spamming, determining who
the author of a document may be, performing gender identification, and language
identification.

We also demonstrated how sentiment analysis is performed. This analysis is
concerned with determining whether a piece of text is positive or negative in nature.
It is also possible to assess other sentiment attributes.

Most of the approaches we used required us to first create a model based on training
data. Normally, this model needs to be validated using a set of test data. Once the
model has been created, it is usually easy to use.

In the next chapter, we will investigate the parsing process and how it contributes to
extracting relationships from text.



## 本章术语对照表


* Spam detection  垃圾邮件检测
* Authorship attribution 作者身份识别
* Sentiment Analysis  情感分析
* Language identification 语种识别
* option extraction 意见抽取
* option mining 意见挖掘
* subjectivity analysis 主观分析
* tag cloud  标签云
* sentiment lexicons 情感词典
* Classifier 分类器
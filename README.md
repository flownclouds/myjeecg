# myjeecg
1.基于JEECG3.5.2，提供多种数据源的代码生成，目前支持Oracle良好；

2.可动态配置数据源；
  可动态配置模板集合，基于freemarker的模板文件；
  可选择需要生成的数据表；
  可导入一个java类，根据配置的模板，生成对应的代码文件；
  
3.目前的这个扩展，主要解决以下的问题；
  动态的选择一个oracle数据源，选择一个数据表；
  根据配置的模板集合如：dao、entity、service、controller、view、js（其中主要是view、js）生成对应的代码文件；
  这个模板如果不合适可以在线修改，重新生成对应的代码；
  这个主要是单表的一体化生成，如果是多表的话，可以在对应的代码文件里再修改下；
  
  根据java类来生成代码，这个就很简单了，主要是项目中用到的entity-dto的互转，大部分这两层代码都定义的差不多，手工互转还是
  很累的，当然如果完全一样也可以用反射，不过为了扩展需要和性能需要不建议反射；这里，只需要定义一个互转的模板，就可以生成需要的代码；
  
  不可能完全做到代码自动化，完全自动化的话对项目的约定很死，也不符合项目的扩展需要；所以能做到半自动代码生成就够了，需要扩展自己改改就行；
  
  
  

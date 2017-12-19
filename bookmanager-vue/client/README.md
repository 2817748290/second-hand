# bookstore

> 本项目基于 Vue 框架，实现一个在线图书购买网站。 [项目展示](https://yongchen0.github.io/bookstore/)

## 技术栈

1. Vue
2. vue-router
3. Vuex
4. axios

## 开发

### 项目页面组件

1. 首页（Home）
2. 主题（Subject）
3. 分类（Category）
4. 购物车（Cart）
5. 图书（Book）
6. 页面头部（PageHeader）
7. 页脚（PageFooter）

页面之间的导航通过 vue-router 插件来实现。

### 首页

首页是项目的入口，分为两部分，网站横幅（Banner）和主题导航（SubjectNav）。

#### 网站横幅

横幅采用三栏布局，由三个组件组成：分类导航（CategoryList），轮播图（Slider），登录窗口（Login）。

分类导航的每一个类别导航到分类页面对应的类别上。

轮播图：展示一些活动相关的信息，每一个 Slide 都是一个图片链接，链接到对应活动的页面，不过本项目并未设置活动页面，所以链接不能跳转（链接的 href 设置为" javascript:; "）。轮播图是通过原生代码实现的。

登陆窗口：当点击登录按钮时，弹出一个弹出层用于登录，包括输入登录账户，忘记密码，注册等常见登录结构，不过登录，忘记密码，注册的功能并未实现，当点击弹出层的登录按钮后，关闭弹出层，登录按钮变为已登录。

#### 主题导航

主题导航有三个区块：热门图书，特价图书，新书上架。

每个区块都是重用相同的模块，用于展示部分主题的图书（这里展示六本图书），并且提供导航到主题页面对应的主题上。

### 主题

主题页面用于展示每个主题所有的图书。

主题页面采用 flex 实现双栏布局，主题导航和图书展示。

### 分类

分类页面用于展示不同类别的所有图书。

主题页面采用 flex 实现双栏布局，主题导航和图书展示。

### 购物车

购物车页面用于结算选中的图书。

### 图书

图书页面用于展现选择的图书相关信息。

### 页面头部

页面头部由两部分组成：网站 Logo 和搜索框。

#### 搜索框

搜索框功能：根据用户输入的关键字（书名或作者名），从 store 对象中的 allBooks  对象中搜索匹配的图书，并将匹配的图书展示到一个无序列表中，当用户点击某一项时，跳转到图书页面，并将点击的项对应的图书渲染到图书页面中。

搜索框涉及几个重要功能：

- 动态搜索

当用户输入关键字的同时，搜索出匹配的图书，所以这里将搜索函数绑定到搜索框的 input 事件上。

- 部分匹配

当我们输入的关键字为一部分书名或者作者名，就应该匹配到符合这部分关键字的所有图书，这个功能通过 String.includes() 方法来实现，迭代所有图书，判断每一本图书的图书名或者作者名字符串是否包含输入的关键字字符串，包含则表示匹配。

不过 String.includes() 方法对于英文是区分大小写的，所以为了忽略大小写的影响，我们先将图书名和作者名通过 String.toLowerCase() 方法将英文部分全部转换成小写，再通过 String.includes() 方法来匹配。

```javascript
// keyword 搜索框输入的关键字
// matchingBooks 用于存储匹配到的图书的数组
searchBooks(keyword) {
    if (keyword == '' || keyword == undefined) {
        this.matchingBooks = [];
        return;
    }
    let lowerCase = keyword.toLowerCase();
    let allBooks = this.$store.getters.getAllBookList;
    this.matchingBooks = allBooks.filter((item, index, array)=>{
        return item.name.toLowerCase().includes(lowerCase) || item.author.toLowerCase().includes(lowerCase);
    });
}
```



### 数据存储

项目中的共享数据通过 Vuex 来管理。

共享数据包括：

1. 网站所有图书
2. 添加到购物车中的图书
3. 各主题所有的图书

图书数据以 JSON 格式保存在在 /static 目录下。

因为网站所有图书和各主题所有的图书在首页就需要读取，所以这两部分的图书数据在首页加载时便通过 axios 获取，并存储到 store 对象中。

而购物车中的数据在每次点击图书的购买按钮时存储到 store 对象中，当在购物中结算后，清空购物车中已存储的图书。
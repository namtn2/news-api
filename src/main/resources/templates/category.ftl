<#import "index.ftl" as homePage/>
<@homePage.page title="Category">

    <#import "/spring.ftl" as spring/>

    <div class="main-banner header-text">
        <div class="container-fluid">
            <div class="owl-banner owl-carousel">
                <#list listCategory as category>
                    <div class="item">
                        <img src="${category.imgUrl}" alt="">
                        <div class="item-content">
                            <div class="main-content">
                                <a href="<@spring.url '/category/' + category.id />"><h4>${category.name}</h4></a>
                                <ul class="post-info">
                                    <li><a href="#">Admin</a></li>
                                    <li><a href="#">May 12, 2020</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <section class="blog-posts grid-system">
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="all-blog-posts">
                        <div class="row">
                            <#list listCategory as category>
                                <div class="col-lg-6">
                                    <div class="blog-post">
                                        <div class="blog-thumb">
                                            <img src="${category.imgUrl}" alt="">
                                        </div>
                                        <div class="down-content">
                                            <span><a href="<@spring.url '/category/' + category.id />">${category.name}</a></span>
                                            <ul class="post-info">
                                                <li>Admin</li>
                                                <li>May 31, 2020</li>
                                            </ul>
                                            <p>${category.name} : Nullam nibh mi, tincidunt sed sapien ut, rutrum
                                                hendrerit velit. Integer auctor a mauris sit amet eleifend.</p>
                                            <div class="post-options">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <ul class="post-tags">
                                                            <li><i class="fa fa-tags"></i></li>
                                                            <li>Best Templates,</li>
                                                            <li>TemplateMo</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <#include "side-bar-news.ftl">
                    <br/>
                    <#include "side-bar-category.ftl">
                </div>

            </div>
        </div>
    </section>

</@homePage.page>
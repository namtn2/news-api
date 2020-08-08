<#import "index.ftl" as newsPage/>
<@newsPage.page title="News">

    <#import "/spring.ftl" as spring/>

    <section class="blog-posts grid-system">
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="all-blog-posts">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="blog-post">
                                    <div class="blog-thumb">
                                    </div>
                                    <div class="down-content">
                                        <span>${newsDetail.title}</span>
                                        <ul class="post-info">
                                            <li>Admin</li>
                                            <li>${newsDetail.createTime}</li>
                                        </ul>
                                        ${newsDetail.content}
                                        <div class="post-options">
                                            <div class="row">
                                                <div class="col-6">
                                                    <ul class="post-tags">
                                                        <li><i class="fa fa-tags"></i></li>
                                                        <li><a href="#">Best Templates</a>,</li>
                                                        <li><a href="#">TemplateMo</a></li>
                                                    </ul>
                                                </div>
                                                <div class="col-6">
                                                    <ul class="post-share">
                                                        <li><i class="fa fa-share-alt"></i></li>
                                                        <li><a href="#">Facebook</a>,</li>
                                                        <li><a href="#"> Twitter</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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

</@newsPage.page>
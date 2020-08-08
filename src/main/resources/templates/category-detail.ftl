<#import "index.ftl" as categoryDetail/>
<@categoryDetail.page title="CategoryDetail">

    <#import "/spring.ftl" as spring/>

    <section class="blog-posts grid-system">
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="all-blog-posts">
                        <div class="row">
                            <#if listNews??>
                                <#list listNews as new>
                                    <div class="col-lg-8">
                                        <div class="blog-post">
                                            <div class="blog-thumb">

                                            </div>
                                            <div class="down-content">
                                                <span><a href="<@spring.url '/news/' + new.id />">${new.title}</a></span>
                                                <ul class="post-info">
                                                    <li>Admin</li>
                                                    <li>May 31, 2020</li>
                                                </ul>
                                                <p>
                                                    <#assign contentStr=(new.content!"")>
                                                    <#if contentStr?length &lt; 300>
                                                        ${contentStr}
                                                    <#else>
                                                        ${contentStr?substring(0,300)} ...
                                                    </#if>
                                                </p>
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
                            <#else>
                                <p>This category haven't have any news yet. Please comeback later !</p>
                            </#if>
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

</@categoryDetail.page>
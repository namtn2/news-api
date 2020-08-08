<#--<#import "index.ftl" as sideBarCate/>-->
<#--<@sideBarCate.page title="SideBarCate">-->

<#import "/spring.ftl" as spring/>

<div class="sidebar">
    <div class="row">
        <div class="col-lg-12">
            <div class="sidebar-item recent-posts">
                <div class="sidebar-heading">
                    <h2><a href="">Recent News</a></h2>
                </div>
                <div class="content">
                    <ul>
                        <#list listNewsAll as new>
                            <li><a href="<@spring.url '/news/' + new.id />">
                                    <h5>${new.title}</h5>
                                    <p>${new.createTime}</p>
                                </a></li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<#--</@sideBarCate.page>-->
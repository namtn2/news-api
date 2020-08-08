<#--<#import "index.ftl" as sideBarCate/>-->
<#--<@sideBarCate.page title="SideBarCate">-->

<#import "/spring.ftl" as spring/>

<div class="sidebar">
    <div class="row">
        <div class="col-lg-12">
            <div class="sidebar-item categories">
                <div class="sidebar-heading">
                    <h2><a href="">Categories</a></h2>
                </div>
                <div class="content">
                    <ul>
                        <#list listCategory as category>
                            <li class="col-lg-4" ><a href="<@spring.url '/category/' + category.id />">${category.name}</a></li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<#--</@sideBarCate.page>-->
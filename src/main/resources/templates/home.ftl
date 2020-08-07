<#import "index.ftl" as homePage/>
<@homePage.page title="HomePage">

        <#import "/spring.ftl" as spring/>

        <div class="slides">
            <div class="slide" id="1">
                <div id="slider-wrapper">
                    <div id="image-slider">
                        <ul>
                            <li class="active-img">
                                <div class="slide-caption">
                                    <h6>New Arrival</h6>
                                    <h2>Beautiful<br>Earth</h2>
                                </div>
                            </li>
                            <li>
                                <div class="slide-caption">
                                    <h6>Latest One</h6>
                                    <h2>Tech<br>Meeting</h2>
                                </div>
                            </li>
                            <li>
                                <div class="slide-caption">
                                    <h6>Your Update</h6>
                                    <h2>Smart<br>Devices</h2>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div id="thumbnail">
                        <ul>
                            <li class="active"><img src="/static/images/thumb-01.jpg" alt="Earth"/></li>
                            <li><img src="/static/images/thumb-02.jpg" alt="Meeting"/></li>
                            <li><img src="/static/images/thumb-03.jpg" alt="Smart"/></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="slide" id="2">
                <div class="content third-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="owl-carousel owl-theme">
                                <#list listCategory as category>
                                    <div class="col-md-12">
                                        <div class="featured-item">
                                            <a href="<@spring.url '/category/' + category.id />"><img
                                                        src=${category.imgUrl} alt=""></a>
                                            <div class="down-content">
                                                <h4>${category.name}</h4>
                                                <h6>Click for more</h6>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

</@homePage.page>
package io.micronaut

import groovy.transform.CompileDynamic
import groovy.xml.MarkupBuilder

trait GuideGroupHtml implements PageElement {

    abstract String getUlId()

    abstract String getImage()

    abstract String getTitle()

    abstract  List<GuideGroupItem> getItems()

    abstract String getDescription()

    @CompileDynamic
    @Override
    String renderAsHtml() {
        renderAsHtmlWithStyleAttr(null)
    }

    String renderAsHtmlWithStyleAttr(String styleAttr) {
        StringWriter writer = new StringWriter()
        MarkupBuilder html = new MarkupBuilder(writer)
        html.div(class: "guidegroup", style: "${styleAttr ?: ''}") {
            div(class: "guidegroupheader") {
                img src: image, alt: title
                h2 {
                    html.mkp.yieldUnescaped title
                }
            }
            ul(id: getUlId() ?: '') {
                if ( description ) {
                    li(class: 'legend', description)
                }
                if ( items ) {
                    for ( GuideGroupItem item : items ) {
                        if ( item.href ) {
                            li {
                                a href: item.href, item.title
                            }
                        } else {
                          li(item.title)
                        }
                    }
                }
            }
        }


        writer.toString()
    }
}

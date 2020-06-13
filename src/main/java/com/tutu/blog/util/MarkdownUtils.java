package com.tutu.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @author tutu 2020/5/2 9:52
 */
public class MarkdownUtils {

    /**
     * markdown格式转HTML格式
     * @param markdown 待转换的markdown格式字符串
     * @return 转换后HTML格式的字符串
     */
    public static String markdownToHtml(String markdown){
        return HtmlRenderer.builder().build().render(Parser.builder().build().parse(markdown));
    }

    public static String markdownToHtmlExtensions(String markdown){
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转成table的HTML
        List<Extension> tableExtension = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
            @Override
            public AttributeProvider create(AttributeProviderContext context) {
                return new CustomAttributeProvider();
            }
        }).build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if(node instanceof Link){
                attributes.put("target", "_blank");
            }
            if(node instanceof TableBlock){
                attributes.put("class", "ui celled table");
            }
        }
    }

    private MarkdownUtils(){}
}

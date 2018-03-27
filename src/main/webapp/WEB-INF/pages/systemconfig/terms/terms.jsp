<%-- 
    Document   : terms
    Created on : Mar 27, 2018, 9:01:24 PM
    Author     : prathibha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />

            <%@include file="/stylelinks.jspf" %>


            <link href='https://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,700,700italic&subset=latin,vietnamese,latin-ext,cyrillic,cyrillic-ext,greek-ext,greek' rel='stylesheet' type='text/css'/>
            <link href='https://fonts.googleapis.com/css?family=Oswald:400,300,700&subset=latin,latin-ext' rel='stylesheet' type='text/css'/>
            <link href='https://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'/>
            <link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300italic,700&subset=latin,greek,greek-ext,vietnamese,cyrillic-ext,cyrillic,latin-ext' rel='stylesheet' type='text/css'/>


            <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css"/>-->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/froala/css/froala_editor.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/froala_style.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/code_view.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/colors.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/emoticons.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/image_manager.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/image.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/line_breaker.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/table.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/char_counter.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/video.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/fullscreen.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/quick_insert.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/plugins/file.css"/>
            <link rel="stylesheet" href=" ${pageContext.request.contextPath}/resources/froala/css/themes/dark.css"/>


            <style>
                body {
                    text-align: center;
                }

                section {
                    width: 81%;
                    margin: auto;
                    text-align: left;
                }

            </style>

            </script>

            <title></title>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>

        <!--body content-->
        <div class="tb-body">
            <s:div id="divmsg">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
            <div class="tb-breadcrumb">User Management > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">

                    <section id="editor" style="font-family: Roboto">
                        <div id='edit' style="margin-top: 30px;">
                            <h1>Dark Theme</h1>
                        </div>
                    </section>

                    <div>
                        <br>
                            <input type="button" value="Get" onclick="get()" />
                            <input type="button" value="Set" onclick="set()" />
                            <input type="button" value="Isempty" onclick="Isempty()" />
                            <input type="button" value="editon" onclick="editon()" />
                            <input type="button" value="editoff" onclick="editoff()" />
                            <input type="button" value="clear" onclick="clears()" />
                    </div>


                </div>
            </div>
        </div>



        <!--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>-->
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/froala_editor.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/align.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/code_beautifier.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/code_view.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/colors.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/draggable.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/emoticons.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/font_size.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/font_family.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/image.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/file.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/image_manager.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/line_breaker.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/link.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/lists.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/paragraph_format.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/paragraph_style.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/video.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/table.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/url.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/entities.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/char_counter.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/inline_style.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/save.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/fullscreen.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/quick_insert.min.js"></script>
        <script type="text/javascript" src=" ${pageContext.request.contextPath}/resources/froala/js/plugins/quote.min.js"></script>

        <script>
                                    function get() {
                                        alert($('#edit').froalaEditor('html.get', true));
                                    }

                                    function set() {
                                        $('#edit').froalaEditor('html.set', '<p>My custom paragraph.</p>');
                                    }

                                    function Isempty() {
                                        alert($('#edit').froalaEditor('core.isEmpty'));
                                    }

                                    function editon() {
                                        $('#edit').froalaEditor('edit.on');
                                    }

                                    function editoff() {
                                        $('#edit').froalaEditor('edit.off');
                                    }

                                    function clears() {
                                        $('#edit').froalaEditor('html.set', '');
                                    }


                                    $(function () {
                                        $.FroalaEditor.DefineIcon('clear', {
                                            NAME: 'remove'
                                        });
                                        $.FroalaEditor.RegisterCommand('clear', {
                                            title: 'Clear HTML',
                                            focus: false,
                                            undo: true,
                                            refreshAfterCallback: true,
                                            callback: function () {
                                                this.html.set('');
                                                this.events.focus();
                                            }
                                        });

                                        $('#edit').froalaEditor({
                                            theme: 'dark',
                                            height: 300,
                                            toolbarButtons: ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'color', 'emoticons', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'insertLink', 'insertImage', 'insertVideo', 'insertFile', 'insertTable', '|', 'quote', 'insertHR', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', '-', 'clear'],
                                            fontFamily: {
                                                "Roboto,sans-serif": 'Roboto',
                                                "Oswald,sans-serif": 'Oswald',
                                                "Montserrat,sans-serif": 'Montserrat',
                                                "'Open Sans Condensed',sans-serif": 'Open Sans Condensed'
                                            },
                                        });

                                    });

        </script>
    </body>
</html>

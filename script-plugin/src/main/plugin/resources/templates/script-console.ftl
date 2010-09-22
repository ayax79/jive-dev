<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <title>Script Console</title>
    <meta name="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="<@s.url value='/plugins/script-plugin/resources/script/jquery-1.4.2.min.js' />"></script>

    <script type="text/javascript">
        $(function() {

            var push_script = function() {
                $.post('<@s.url action="script-console" method="process" />', { script: $('#input-box').val() },
                        function(data) {
                            $('#output-box').append("> " + data.msg + "<br />"); 
                        }, "json");
            };

            $('#submit-script').click(push_script);
        });

    </script>
    <style type="text/css">
        #output-box {
            outline: 1px solid black;
            width: 800px;
            height: 400px;
            margin-top: 10px;
            padding: 3px 3px 3px 3px;
        }
    </style>

</head>
<body>
<h1>Jive Script Console</h1>

<div id="input-box-container">
    <textarea id="input-box" rows="25" cols="100"></textarea>
</div>
<button id="submit-script">Submit</button>

<div id="output-box"></div>

<div id="help-box">
    <ul>
        ${action.help}
    </ul>
</div>

</body>
</html>

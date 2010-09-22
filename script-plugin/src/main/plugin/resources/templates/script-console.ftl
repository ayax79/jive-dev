<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <title>Script Console</title>
    <meta name="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript"
            src="<@s.url value='/plugins/script-plugin/resources/script/jquery-1.4.2.min.js' />"></script>

    <script type="text/javascript">
        var push_script = function() {
            $.post('<@s.url action="script-console" method="process" />', { script: $('#input-box').val() },
                    function(data) {
                        var result = data.result;
                        if (result) {
                            $('#output-box').append('<li class="result-msg console-msg">result: ' + result + '</li>');
                        }

                        var output = data.output;
                        if (output) {
                            $('#output-box').append('<li class="output-msg console-msg">output: ' + output + '</li>');
                        }

                        var error = data.error;
                        if (error) {
                            $("#output-box").append('<li class="error-msg console-msg">error: ' + error + '</li>');
                        }
                    }, "json");
        };


        $(function() {


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

        .console-msg {
            list-style-type: none;
            font-weight: bold;
        }

        .error-msg {
            color: red;
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

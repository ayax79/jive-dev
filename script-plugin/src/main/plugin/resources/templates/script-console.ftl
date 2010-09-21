<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
          "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>Script Console</title>
    <meta name="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="<@s.url value='/plugins/script-plugin/resources/script/jquery-1.4.2.min.js' />"></script>
    <script type="text/javascript" src="<@s.url value='/plugins/script-plugin/resources/script/jquery.console.js' />"></script>
    <!-- Everything beyond this point is user-customized -->
    <script type="text/javascript">
      $(document).ready(function(){
         /* First console */
         var console1 = $('<div class="console1">');
         $('body').append(console1);
         var controller1 = console1.console({
           promptLabel: 'Demo> ',
           commandValidate:function(line){
             if (line == "") return false;
             else return true;
           },
           commandHandle:function(line, report){
               try {
                   $.post('<@s.url action="script-console" method="process" />',
                   { script: line }, function(data) {
                       report([
                           {msg: " => " + data.msg, className:"jquery-console-message-value"}
                       ]);
                   }, "json");
               } catch (e) {
                   return e.toString();
               }
           },
           autofocus:true,
           animateScroll:true,
           promptHistory:true,
           charInsertTrigger:function(keycode,line){
              // Let you type until you press a-z
              // Never allow zero.
              return !line.match(/[a-z]+/) && keycode != '0'.charCodeAt(0);
           }
         });
       });
    </script>
    <style type="text/css" media="screen">
      /* First console */
      div.console1 { font-size: 14px }
      div.console1 div.jquery-console-inner 
       { width:900px; height:200px; background:#333; padding:0.5em;
         overflow:auto }
      div.console1 div.jquery-console-prompt-box
       { color:#fff; font-family:monospace; }
      div.console1 div.jquery-console-focus span.jquery-console-cursor 
       { background:#fefefe; color:#333; font-weight:bold }
      div.console1 div.jquery-console-message-error
       { color:#ef0505; font-family:sans-serif; font-weight:bold;
         padding:0.1em; }
      div.console1 div.jquery-console-message-value
       { color:#1ad027; font-family:monospace;
         padding:0.1em; }
      div.console1 div.jquery-console-message-type
       { color:#52666f; font-family:monospace;
         padding:0.1em; }
      div.console1 span.jquery-console-prompt-label { font-weight:bold }
    </style>
  </head>
  <body>
    <noscript>
      <p>
        <strong>Please enable JavaScript or upgrade your browser.</strong>
      </p>
    </noscript>
    <h1>Jive Script Console</h1>
  </body>
</html>

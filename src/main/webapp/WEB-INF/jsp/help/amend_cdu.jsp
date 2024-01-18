<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Amend CDU - Help</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body>

	<header>
			
		<h2>Amend CDU - Help</h2>	
			
	</header>

	<div class="container-fluid">
	
		<div class="row">
		
			<div class="col-md-12">
				
				<h3>Amend CDU</h3>

				<p>The &quot;Location&quot; field is the human-readable name for the location of the display (e.g. Court 5, Reception or Robing Room). This is shown as part of the title for all pages, but is especially important for the court rooms as it forms a critical part of the title.</p> 

				<p>The &quot;Description&quot; field stores details about accessing the CDU and is used to help locate the CDU (e.g. located in ceiling tiled, locked with key 405). This is free text and does not store anything about the CDU that will ever be seen outside the manager application.</p> 

				<p>The &quot;Notification&quot; field is a message you wish to display on the CDU screen (e.g. Welcome to Snaresbrook Crown Court). This is shown in a marquee along the bottom of the CDU screen.</p> 

				<p>The &quot;Rotation Rate&quot; field is the amount of time spent on one frame before changing to the next. For example, a value of 5 would mean a 4 page document will take 20 seconds from start to end to display, with each page staying on the display for 5 seconds.</p> 

				<p>The &quot;Weighting&quot; field has a value of 1 or 2 where 1 is for a low priority display (e.g. a single court room) and 2 is for those screens that have a higher visibility or pairs of eyes on them (e.g. Reception, Public Canteen). If there is any doubt, then assign a value of 2.</p> 

				<p>The &quot;Known Offline&quot; field has a value of Yes or No. A value of Yes removes the CDU from the RAG status criteria. Changes to this field only take effect the next time the court site is contacted for its latest RAG status.</p> 

			</div>
			<!-- /col-md-12 -->
		</div>
		<!-- /row -->
	</div>
	<!-- /container-fluid -->

	<%@ include file="../common/scripts.jsp"%>
     
</body>

</html>

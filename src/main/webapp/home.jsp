<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon"
	href="http://www.interseroh.de/fileadmin/interseroh/system/templates/images/favicon.ico"
	type="image/x-icon">

<title>ReportingCockpit for BIRT</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap theme -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/paper/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="jumbotron.css" rel="stylesheet">
<link href="individual.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="http://getbootstrap.com/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script
	src="http://getbootstrap.com/assets/js/ie-emulation-modes-warning.js"></script>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script
	src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<!-- Navigation bar -->
	<div class="navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">${interserohText}</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#introduction">Einführung</a></li>
					<li><a href="#faq">FAQ</a></li>

					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li><a href="entsorgung">ReportingCockpit</a></li>
						<li><a href="<c:url value="/login?logout" />">Angemeldet
								als: ${pageContext.request.userPrincipal.name} &raquo; Abmelden</a></li>
					</c:if>

					<c:if test="${pageContext.request.userPrincipal.name == null}">
						<li><a href="entsorgung">Anmelden</a></li>
					</c:if>

					<li><a href="#contact">Kontakt</a></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Anwendungen
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="entsorgung">ReportingCockpit</a></li>
							<li><a href="${mainWebapp2Link}" target="_blank">${mainWebapp2Name}</a></li>
							<li><a href="${mainWebapp3Link}" target="_blank">${mainWebapp3Name}</a></li>
							<li><a href="${mainWebapp4Link}" target="_blank">${mainWebapp4Name}</a></li>
						</ul></li>
				</ul>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1>ReportingCockpit for BIRT</h1>
			<p>xxxxx.……</p>
			<p>
				<a class="btn btn-info btn-lg" role="button" href="#introduction">xxx &raquo;</a> <a class="btn btn-warning btn-lg"
					href="entsorgung" role="button">ReportingCockpit xxx
					&raquo;</a>
			</p>
		</div>
	</div>

	<!-- Container -->
	<div class="container marketing" id="colors-select">
		<!-- Surveys columns -->
		<div class="row row-buffer-top-bottom-50px row-buffer-bottom-100px">
			<div class="col-md-6">
				<img class="img-thumbnail" src="images/home-inbox.jpg"
					alt="Generic placeholder image"
					style="width: 400px; height: 300px;">
				<h2>Feature 1</h2>
				<p class="lead">xxx</p>
				<p>
					<a class="btn btn-primary" href="#introduction" role="button">Details
						anschauen &raquo;</a>
				</p>
			</div>
			<div class="col-md-6">
				<img class="img-thumbnail" src="images/home-manuell.jpg"
					alt="Manueller Auftrag" style="width: 400px; height: 300px;">
				<h2>Feature 2</h2>
				<p class="lead">xxx</p>
				<p>
					<a class="btn btn-primary" href="#introduction" role="button">Details
						anschauen &raquo;</a>
				</p>
			</div>
		</div>
	</div>

	<div id="introduction" class="row-buffer-bottom-50px"></div>

	<!-- Jumbotron for a introduction -->
	<div class="jumbotron color-jumbotron-introduction">
		<div class="container">
			<h1>Einführung zum ReportingCockpit</h1>
		</div>
	</div>

	<div class="container marketing" id="colors-select">
		<!-- START THE FEATURETTES -->
		<div class="row featurette">
			<div class="col-md-9">
				<h2 class="featurette-heading">
					xxx <span class="text-muted">xxx.</span>
				</h2>
				<p class="lead">xxx</p>
			</div>
			<div class="col-md-3">
				<img class="featurette-image img-responsive img-rounded"
					src="images/xxx.jpg"
					alt="xxx">
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-4">
				<img class="featurette-image img-responsive img-rounded"
					src="images/xxx" alt="xxx">
			</div>
			<div class="col-md-8">
				<h2 class="featurette-heading">
					Feature... <span class="text-muted">xxx</span>
				</h2>
				<p class="lead">xxx</p>
			</div>
		</div>

		<hr class="featurette-divider">
		
		<!-- /END THE FEATURETTES -->
	</div>
	<!-- /container -->

	<div id="faq" class="row-buffer-bottom-50px"></div>

	<!-- Jumbotron for a FAQ -->
	<div class="jumbotron color-jumbotron-faq">
		<div class="container">
			<h1>FAQ</h1>
		</div>
	</div>

	<div class="container marketing" id="colors-select">
		<!-- START THE FAQs -->
		<div class="row featurette">
			<div class="col-md-12">
				<p class="lead">Q: xxx</p>
				<p class="lead">A: yyy</p>
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-12">
				<p class="lead">Q: xxx</p>
				<p class="lead">A: yyy</p>
			</div>
		</div>
		<!-- /END THE FEATURETTES -->
	</div>
	<!-- /container -->

	<!-- Jumbotron for a apps -->
	<div id="contact" class="jumbotron color-jumbotron-apps">
		<div class="container">
			<div class="row featurette">
				<div class="col-md-8">
					<h2 class="featurette-heading">
						Kontakt. <span class="text-muted">Handbuch, <a
							href="http://it-service/sps/portal" target="_blank">Tickets</a>, <a
							href="http://it-service/sps/portal" target="_blank">Bugs</a> und Support: xxx!
						</span>
					</h2>
				</div>
				<div class="col-md-2">
					<img class="featurette-image img-responsive img-circle"
						src="images/home-person1.jpg" alt="Photo1"
						style="width: 240px; height: 170px;">
				</div>
				<div class="col-md-2">
					<img class="featurette-image img-responsive img-circle"
						src="images/home-person2.jpg" alt="Photo2"
						style="width: 240px; height: 170px;">
				</div>
			</div>
		</div>
	</div>

	<div class="container marketing">
		<hr class="featurette-divider">
		<footer>
			<p>&copy; Interseroh 2015 - Version: ${version}</p>
		</footer>
	</div>
	<!-- /container -->

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script src="http://getbootstrap.com/assets/js/docs.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script
		src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>

	<script type="text/javascript">
		$(".nav a").on("click", function() {
			$(".nav").find(".active").removeClass("active");
			$(this).parent().addClass("active");
		});
	</script>
</body>
</html>

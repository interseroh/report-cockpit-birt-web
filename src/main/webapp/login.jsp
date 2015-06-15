<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon"
	href="http://www.interseroh.de/fileadmin/interseroh/system/templates/images/favicon.ico" type="image/x-icon">

<title>ReportingCockpit for BIRT</title>

<!-- Bootstrap core CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">

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

<body onload='document.loginForm.username.focus();'>
	<div class="container-fluid main">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="alert alert-info" role="alert">${msg}</div>
				</c:if>
			</div>
			<div class="col-md-4"></div>
		</div>

		<div class="col-md-12">
			<div class="modal-dialog" style="margin-bottom: 0">
				<div class="modal-content">
					<div class="panel-heading">
						<h3 class="panel-title">Anmelden</h3>
					</div>
					<div class="panel-body">
						<form role="form" name='loginForm'
							action="<c:url value='/login' />" method='POST'>
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="Benutzername"
										name="username" type="text" autofocus="">
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Passwort"
										name="password" type="password" value="">
								</div>
								<div>
									<input type="submit" class="btn btn-sm btn-success"
										value="Einloggen" name="submit">
								</div>

								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

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
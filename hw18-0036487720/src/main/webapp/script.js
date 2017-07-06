$(document)
		.ready(
				function() {
					$
							.get(
									"tags",
									function(data) {
										$
												.each(
														data,
														function(index, element) {
															$("#demo")
																	.append(
																			"<input ' type='button' id="
																					+ element
																					+ " value="
																					+ element
																					+ " onclick=callThumbnail(this.id); return false;' /> ");
														});
									}, "json");
				});
function callThumbnail(tag) {
	$("#realImg").html("&nbsp;");
	$
			.get(
					"thumbnail?tag=" + tag,
					function(data) {
						$("#thumb").html("");
						$
								.each(
										data,
										function(index, element) {
											$("#thumb")
													.append(
															"<img name='"
																	+ element
																	+ "'src='getThumbnail?name="
																	+ element
																	+ "' onclick='callImg(this.name)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
										});
					}, "json");
}

function callImg(path) {
	$.get("information?name=" + path, function(array) {
		$("#realImg").html("&nbsp;");
		$("#realImg").append("<img src='realImage?path=" + path + "'/>'");
		$("#realImg").append("<p>Opis slike: " + array[0] + "</p>");
		$("#realImg").append("<p>Tagovi: " + array[1] + "</p>");
	}, "json");
}
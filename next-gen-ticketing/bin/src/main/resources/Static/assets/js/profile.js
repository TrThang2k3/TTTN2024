const app = angular.module("my-app", [])
app.controller("my-ctrl", function($scope, $http) {
	let host = "http://localhost:8080/nextgen.com/rest/accounts"

	// kiểm tra cài extension Phantom
	$scope.checkPhantomInstalled = function() {
		let result = window.isPhantomInstalled
		if (!result) {
			$scope.installPhantomMessage = "Please install Phantom extension to connect wallet!"
		} else {
			$scope.installPhantomMessage = ""
		}
	}

	// kết nối ví
	$scope.connect = async function(accountID) {
		await window.phantom.solana.connect()
		let publicKey = window.phantom.solana.publicKey.toBase58()
		$http.put(`${host}/${accountID}/wallet-address`, publicKey).then(resp => {
			window.location.reload()
			// location.href = "http://localhost:8080/nextgen.com/account/profile"
			console.log("Success", resp)
		}).catch(error => {
			console.log("Error", error)
		})
	}

	// tạo ví mới
	$scope.createSemiWallet = function(accountID) {
		var myHeaders = new Headers();
		myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");
		myHeaders.append("Content-Type", "application/json");

		var raw = JSON.stringify({
			"password": $scope.newWalletPassword
		});

		var requestOptions = {
			method: 'POST',
			headers: myHeaders,
			body: raw,
			redirect: 'follow'
		};

		fetch("https://api.shyft.to/sol/v1/semi_wallet/create", requestOptions)
			.then(response => response.text())
			.then(result => {
				console.log(result)
				let objectResult = JSON.parse(result)
				$http.put(`${host}/${accountID}/wallet-address`, objectResult.result.wallet_address).then(resp => {
					window.location.reload()
					console.log("Success", resp)
				}).catch(error => {
					console.log("Error", error)
				})
			})
			.catch(error => console.log('error', error));
	}

	$scope.onImageEdit = async (imgUrl, imgName) => {
		var imgExt = getUrlExtension(imgUrl);

		const response = await fetch(imgUrl);
		const blob = await response.blob();
		const file = new File([blob], imgName + "." + imgExt, {
			type: blob.type,
		});

		return file;

	}

	$scope.mintNft = function(ticketId) {
		$http.get(`http://localhost:8080/nextgen.com/rest/tickets/${ticketId}`).then(resp => {
			var ticket = resp.data
			console.log("Success", resp)

			var imgSrc = document.getElementById('ticketImage' + ticketId).getAttribute('src')
			console.log(imgSrc)
			var file = $scope.onImageEdit(imgSrc, "Monthly ticket.jpeg")
			var name = ticket.publisher.name + "Monthly Ticket"
			var symbol = "MT"

			var myHeaders = new Headers();
			myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");

			var formdata = new FormData();
			formdata.append("network", "devnet");
			formdata.append("creator_wallet", "A9gyWK9tZJ7cBgDxdoAp74oaxJHmxjibzJ6ngCVKVZDN");
			formdata.append("name", name);
			formdata.append("symbol", symbol);
			formdata.append("description", ticket.description);
			formdata.append("attributes", '[{"price": ' + ticket.price + ',"shelftime": ' + ticket.shelftime + '}]');
			formdata.append("external_url", "https://shyft.to");
			formdata.append("max_supply", "1");
			formdata.append("royalty", "5");
			formdata.append("image", file, "Monthly ticket.jpeg");
			formdata.append("fee_payer", "AaYFExyZuMHbJHzjimKyQBAH1yfA9sKTxSzBc6Nr5X4s");

			var requestOptions = {
				method: 'POST',
				headers: myHeaders,
				body: formdata,
				redirect: 'follow'
			};

			fetch("https://api.shyft.to/sol/v2/nft/create", requestOptions)
				.then(response => response.text())
				.then(result => console.log(result))
				.catch(error => console.log('error', error));

		}).catch(error => {
			console.log("Error", error)
		})
	}

	$scope.checkPhantomInstalled()
})
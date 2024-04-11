const app = angular.module("my-app", [])
app.run(function($http, $rootScope) {
	$http.get(`http://localhost:8080/nextgen.com/rest/authentication`).then(resp => {
		if (resp.data) {
			$auth = $rootScope.$auth = resp.data;
			$http.defaults.headers.common["Authorization"] = $auth.token;
		}
	});
})
app.controller("my-ctrl", function($scope, $http, $rootScope, $timeout) {
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

	// lấy nft vé trong ví
	$scope.readNfts = function() {
		$http.get(`http://localhost:8080/nextgen.com/rest/authentication`).then(resp => {
			if (resp.data) {
				var walletAddress = resp.data.account.walletAddress;
				if (walletAddress != null) {
					var myHeaders = new Headers();
					myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");
					myHeaders.delete("Content-Type");

					var requestOptions = {
						method: 'GET',
						headers: myHeaders,
						redirect: 'follow'
					};

					fetch(`https://api.shyft.to/sol/v2/nft/read_all?network=devnet&address=${walletAddress}&page=1&size=32&update_authority=A9gyWK9tZJ7cBgDxdoAp74oaxJHmxjibzJ6ngCVKVZDN`, requestOptions)
						.then(response => response.text())
						.then(result => {
							let objectResult = JSON.parse(result)
							$scope.nfts = objectResult.result.nfts
							console.log($scope.nfts)
						})
						.catch(error => console.log('error', error));
				}
			}
		});
	}

	$scope.invoice = {
		get buyer() {
			return { id: $rootScope.$auth.account.id }
		},
		invoiceDate: new Date(),
		createInvoice(sellerId, nft, payment) {
			this.seller = { id: sellerId }
			this.nft = nft
			this.amount = nft.ticket.price
			this.payment = payment
			var data = angular.copy(this)
			$http.post("http://localhost:8080/nextgen.com/rest/invoices", data).then(resp => {
				alert("Purchase susscess!")
				$timeout(location.href = "http://localhost:8080/nextgen.com/account/profile", 3)
				console.log(resp)
			}).catch(error => {
				alert("Đặt hàng lỗi")
				console.log(error)
			})
		}
	}

	$scope.nft = {
		createDate: new Date(),
		get account() {
			return { id: $rootScope.$auth.account.id }
		},
		createNft(nftAddress, ticket) {
			this.nftAddress = nftAddress
			this.ticket = ticket
			var data = angular.copy(this)
			$http.post("http://localhost:8080/nextgen.com/rest/nfts", data).then(resp => {
				var nft = resp.data
				$scope.invoice.createInvoice(ticket.publisher.account.id, nft, $scope.payment)
				console.log(resp)
			}).catch(error => {
				console.log(error)
			})
		}
	}

	$scope.toDataURL = url => fetch(url)
		.then(response => response.blob())
		.then(blob => new Promise((resolve, reject) => {
			const reader = new FileReader()
			reader.onloadend = () => resolve(reader.result)
			reader.onerror = reject
			reader.readAsDataURL(blob)
			console.log(blob)
		}))

	$scope.mintNft = function(ticketId) {
		$http.get(`http://localhost:8080/nextgen.com/rest/tickets/${ticketId}`).then(resp => {
			var receiver = $rootScope.$auth.account.walletAddress
			var ticket = resp.data
			console.log("Success", resp)

			fetch(`http://localhost:8080/img/${ticket.image}`)
				.then(response => response.blob())
				.then(blob => new Promise((resolve, reject) => {
					const reader = new FileReader()
					reader.onloadend = () => resolve(reader.result)
					reader.onerror = reject
					reader.readAsDataURL(blob)

					var imageFile = blob
					var name = ticket.publisher.name + " Ticket"
					var symbol = "NGT"
					var attributes = '[{"trait_type":"price","value":"' + ticket.price +
						'VND"}, {"trait_type":"shelftime","value":"' + ticket.shelftime +
						'"}, {"trait_type":"createDate","value":"' + new Date() + '"}]'

					var myHeaders = new Headers();
					myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");
					myHeaders.delete("Content-Type");

					var formdata = new FormData();
					formdata.append("network", "devnet");
					formdata.append("private_key", "4kbEMgVHAYBdGpvrrHwH9LT21kzXQ9SQtcZtm9suqrAATxc9zn6KS6yqq5R21MoWzudNmxf52fb6nDGWMf1xkgZi");
					formdata.append("name", name);
					formdata.append("symbol", symbol);
					formdata.append("description", ticket.description);
					formdata.append("attributes", attributes);
					formdata.append("max_supply", "1");
					formdata.append("royalty", "100");
					formdata.append("file", imageFile);
					formdata.append("receiver", receiver);

					var requestOptions = {
						method: 'POST',
						headers: myHeaders,
						body: formdata,
						redirect: 'follow'
					};

					fetch("https://api.shyft.to/sol/v1/nft/create", requestOptions)
						.then(response => response.text())
						.then(result => {
							console.log(result)
							let objectResult = JSON.parse(result)
							$scope.nft.createNft(objectResult.result.mint, ticket)
						})
						.catch(error => console.log('error', error));
				}))
		}).catch(error => {
			console.log("Error", error)
		})
	}

	$scope.getWalletBalance = function() {
		$http.get(`http://localhost:8080/nextgen.com/rest/authentication`).then(resp => {
			if (resp.data) {
				var address = resp.data.account.walletAddress;
				if (address != null) {
					var myHeaders = new Headers();
					myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");

					var requestOptions = {
						method: 'GET',
						headers: myHeaders,
						redirect: 'follow'
					};
					fetch(`https://api.shyft.to/sol/v1/wallet/balance?network=devnet&wallet=${address}`, requestOptions)
						.then(response => response.text())
						.then(result => {
							console.log(result)
							let objectResult = JSON.parse(result)
							return objectResult.result.balance
						})
						.catch(error => console.log('error', error));
				} else {
					return 0
				}
			}
		});
	}
	
	$scope.disabledAfterPurchase = false

	$scope.purchaseBySol = function(ticketId) {
		$scope.disabledAfterPurchase = true
		$scope.payment = "sol"
		var exchageRate = 4342169.884
		var walletBalance = $scope.getWalletBalance() * exchageRate

		$http.get(`http://localhost:8080/nextgen.com/rest/tickets/${ticketId}`).then(resp => {
			var ticket = resp.data
			console.log("Success", resp)

			var price = ticket.price
			if (price > walletBalance) {
				alert("The balance in the account is not enough to make the transaction")
				location.href = "http://localhost:8080/nextgen.com/ticket-gallery"
			} else {
				var myHeaders = new Headers();
				myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");
				myHeaders.append("Content-Type", "application/json");

				var amount = Math.ceil(price / exchageRate * 1000000) / 1000000
				var raw = JSON.stringify({
					"network": "devnet",
					"from_address": $rootScope.$auth.account.walletAddress,
					"to_address": "A9gyWK9tZJ7cBgDxdoAp74oaxJHmxjibzJ6ngCVKVZDN",
					"amount": amount
				});

				var requestOptions = {
					method: 'POST',
					headers: myHeaders,
					body: raw,
					redirect: 'follow'
				};

				fetch("https://api.shyft.to/sol/v1/wallet/send_sol", requestOptions)
					.then(response => response.text())
					.then(async result => {
						var data = JSON.parse(result);
						console.log(data)
						var encodedTransaction = data.result.encoded_transaction

						try {
							const signedTransaction = await window.phantom.solana.signTransaction(solanaWeb3.Transaction.from(Uint8Array.from(atob(encodedTransaction), c => c.charCodeAt(0))));
							const connection = new solanaWeb3.Connection("https://api.devnet.solana.com");
							const signature = await connection.sendRawTransaction(signedTransaction.serialize());
							console.log("Transaction sent with signature:", signature);
							const confirmed = await connection.getSignatureStatus(signature);
							console.log("Transaction confirmed:", confirmed);
							$scope.mintNft(ticketId)
						} catch(error) {
							alert("Failed to purchase")
							console.log('error', error)
						}
					})
					.catch(error => console.log('error', error));
			}
		})
	}

	$scope.purchaseBySol2 = function(tradingId) {
		$scope.payment = "sol"
		var exchageRate = 4342169.884
		var walletBalance = $scope.getWalletBalance() * exchageRate

		$http.get(`http://localhost:8080/nextgen.com/rest/trading-nfts/${tradingId}`).then(resp => {
			var tradingNft = resp.data
			console.log("Success", resp)

			var price = tradingNft.price
			if (price > walletBalance) {
				alert("The balance in the account is not enough to make the transaction")
				location.href = "http://localhost:8080/nextgen.com/ticket-gallery"
			} else {
				var myHeaders = new Headers();
				myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");
				myHeaders.append("Content-Type", "application/json");

				var amount = Math.ceil(price / exchageRate * 1000000) / 1000000
				var raw = JSON.stringify({
					"network": "devnet",
					"from_address": $rootScope.$auth.account.walletAddress,
					"to_address": "BqNdMCoGrScQvGMcGBckVyyQXWymDVzD17EsPVj2ZLLV",
					"amount": amount
				});

				var requestOptions = {
					method: 'POST',
					headers: myHeaders,
					body: raw,
					redirect: 'follow'
				};

				fetch("https://api.shyft.to/sol/v1/wallet/send_sol", requestOptions)
					.then(response => response.text())
					.then(result => {
						console.log(result)

						var data = JSON.stringify({
							"network": "devnet",
							"token_address": tradingNft.nft.nftAddress,
							"from_address": "3XN5BcP9BGowKL5ukwebUSQeBAsQnHpR246USHaLyo3aY799GEesvGJScW3qcebaDwV6t32JDhFXSEk3LnPdps6d",
							"to_address": $rootScope.$auth.account.walletAddress,
							"transfer_authority": false,
							"fee_payer": "BqNdMCoGrScQvGMcGBckVyyQXWymDVzD17EsPVj2ZLLV"
						});

						var requestOptions = {
							method: 'POST',
							headers: myHeaders,
							body: data,
							redirect: 'follow'
						};

						fetch("https://api.shyft.to/sol/v1/nft/transfer", requestOptions)
							.then(response => response.text())
							.then(result => {
								console.log(result)
								alert("Purchase")
							})
							.catch(error => console.log('error', error));
					})
					.catch(error => console.log('error', error));
			}
		}).catch(error => {
			console.log("Error", error)
		})
	}
	
	$scope.disabledAfterWithdraw = false

	$scope.withdraw = function(walletAddress, balance, id) {
		$scope.disabledAfterWithdraw = true
		if (balance == 0 || balance == null) {
			alert("Your wallet balance is zero")
			return
		}
		if (walletAddress == null || walletAddress == '') {
			alert("Please connect your Phantom wallet")
			return
		}
		var myHeaders = new Headers();
		myHeaders.append("x-api-key", "vLX6bZRAvPd2DXfe");
		myHeaders.append("Content-Type", "application/json");

		var amount = Math.ceil(balance / 4342169.884 * 1000000) / 1000000
		var raw = JSON.stringify({
			"network": "devnet",
			"from_address": "A9gyWK9tZJ7cBgDxdoAp74oaxJHmxjibzJ6ngCVKVZDN",
			"to_address": walletAddress,
			"amount": amount
		});

		var requestOptions = {
			method: 'POST',
			headers: myHeaders,
			body: raw,
			redirect: 'follow'
		};

		fetch("https://api.shyft.to/sol/v1/wallet/send_sol", requestOptions)
			.then(async response => {
				var resp = await response.json()
				var encodeTransaction = resp.result.encoded_transaction
				var raw2 = JSON.stringify({
					"network": "devnet",
					"private_keys": [
						"4kbEMgVHAYBdGpvrrHwH9LT21kzXQ9SQtcZtm9suqrAATxc9zn6KS6yqq5R21MoWzudNmxf52fb6nDGWMf1xkgZi"
					],
					"encoded_transaction": encodeTransaction,
					"commitment": "finalized"
				})
				var requestOptions2 = {
					method: 'POST',
					headers: myHeaders,
					body: raw2,
					redirect: 'follow'
				};

				fetch("https://api.shyft.to/sol/v1/wallet/sign_transaction", requestOptions2)
					.then(response => response.text())
					.then(result => {
						var resp = JSON.parse(result)
						console.log(resp)
						if (resp.success == true) {
							$http.put(`http://localhost:8080/nextgen.com/rest/accounts/${id}/balance`, 0.0).then(resp => {
								alert("Withdrew " + amount + " sol to your wallet")
								console.log("Success", resp.data)
								window.location.reload()
							}).catch(error => {
								console.log("Error", error)
							})
						}
					})
					.catch(error => console.log('error', error));
			})
			.catch(error => console.log('error', error));
	}

	$scope.checkPhantomInstalled()
})
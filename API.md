<h1>Solana Mobile Wallet Capacitor API</h1>
<p>The Wallet plugin in its current state is designed to replicate the Solana Mobile 'fakewallet' functionality.</p>
<p>The Wallet plugin works by;</p>
<ul>
<li>Listening for the URL the Wallet was launched with using the getLaunchUrl() method provided as part of the <a href="https://capacitorjs.com/docs/apis/app"> Capacitor App plugin</a>.</li>
<li>Launching a process which handles, verification, payload signing and signing & sending transactions as well as session termination.</li>
<li>Registering listeners for these events so the correct UI can be triggered in the web app.</li>
<li>After UI has been displayed user choices (i.e. approve or decline) are captured by the userConfirmation() method. This passes the user's response back into the active process so the request lifecycle can be completed.</li>
</ul>

<h2>Demo</h2>
<p>A demo implementation in Angular is <a href="https://github.com/nolsonlabs/solana-capacitor-wallet-demo">here</a>.</p>

<p>Key code for triggering correct UI is in app.component.ts</p>

<docgen-index>

* [`processLaunch(...)`](#processlaunch)
* [`listenMobileWalletAdapterServiceEvents(...)`](#listenmobilewalletadapterserviceevents)
* [`userConfirmation(...)`](#userconfirmation)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### processLaunch(...)

```typescript
processLaunch(options: { uri: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ uri: string; }</code> |

--------------------


### listenMobileWalletAdapterServiceEvents(...)

```typescript
listenMobileWalletAdapterServiceEvents(options: { uri: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ uri: string; }</code> |

<p>Listener 'incomingRequest' returns the following event types:</p>
<ul>
<li>AuthorizeDapp</li>
<li>SignPayloads</li>
<li>SignAndSendTransactions</li>
</ul>
--------------------


### userConfirmation(...)

```typescript
userConfirmation(options: { authorized: boolean; }) => Promise<void>
```

| Param         | Type                                  |
| ------------- | ------------------------------------- |
| **`options`** | <code>{ authorized: boolean; }</code> |

--------------------

</docgen-api>





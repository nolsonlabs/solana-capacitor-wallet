# solana-mobile-wallet-capacitor

Capacitor plugin for Solana Mobile Wallet SDK

## Install

```bash
npm install solana-mobile-wallet-capacitor
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`processLaunch(...)`](#processlaunch)
* [`listenMobileWalletAdapterServiceEvents(...)`](#listenmobilewalletadapterserviceevents)
* [`userConfirmation(...)`](#userconfirmation)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


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

import type { Plugin } from '@capacitor/core';

export interface SolanaMobileWalletPlugin extends Plugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  processLaunch(options: { uri: string }): Promise<void>;
  listenMobileWalletAdapterServiceEvents(options: { uri: string }): Promise<void>;
  userConfirmation(options: { authorized: boolean }): Promise<void>;
}

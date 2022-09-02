import { WebPlugin } from '@capacitor/core';

import type { SolanaMobileWalletPlugin } from './definitions';

export class SolanaMobileWalletWeb
  extends WebPlugin
  implements SolanaMobileWalletPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async processLaunch(options: { uri: string }): Promise<void> {
    console.log('PROCESS LAUNCH' + options)
  }

  async listenMobileWalletAdapterServiceEvents(options: { uri: string }): Promise<void> {
    console.log('PROCESS LAUNCH' + options)
  }

  async userConfirmation(options: { authorized: boolean }): Promise<void> {
    console.log('PROCESS LAUNCH' + options)
  }
}

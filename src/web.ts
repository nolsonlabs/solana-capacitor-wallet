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
}

export interface SolanaMobileWalletPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

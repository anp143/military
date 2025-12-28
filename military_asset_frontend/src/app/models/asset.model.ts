export interface Asset {
  id?: number;
  name: string;
  type: string;
  quantity: number;
  base: { id: number; name?: string; location?: string };
}

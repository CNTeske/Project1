export class Reimbursement {
  id: number;
  amount: number;
  submitted: Date;
  resolved: Date;
  description: string;
  receipt: Blob;
  author: number;
  resolver: number;
  status: string;
  type: string;
}
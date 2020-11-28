package es.uclm.esi.payload.request;



public class CalendarioMesRequest {
		private int mes;
		public CalendarioMesRequest(int mes, int ano) {
			super();
			this.mes = mes;
			this.ano = ano;
		}
		public int getMes() {
			return mes;
		}
		public void setMes(int mes) {
			this.mes = mes;
		}
		public int getAno() {
			return ano;
		}
		public void setAno(int ano) {
			this.ano = ano;
		}
		private int  ano;
		

}

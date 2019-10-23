package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import com.google.gson.Gson;

import dao.DaoGanttChart;
import entidades.Projeto;
import entidades.Series;


@WebServlet("/pages/buscarDatasPlanejamento")
public class BuscarDatasPlanejamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoGanttChart daoGanttChart = new DaoGanttChart();
	
    public BuscarDatasPlanejamento() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			/*Criamos uma list do objeto Projeto e setamos que ele receba os dados do resultado do método
			 * getProjetos do DaoGanttChart
			 * Transforma toda a lista de dados em JSON*/
			List<Projeto> projetos = daoGanttChart.getProjetos();

			if (!projetos.isEmpty()) {
				String grantJson = new Gson().toJson(projetos);

				response.setStatus(200);
				response.getWriter().write(grantJson);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Series seriesUpdate = new Series();
			
			seriesUpdate.setId(Long.parseLong(request.getParameter("serie")));
			seriesUpdate.setProjeto(Long.parseLong(request.getParameter("projeto")));
			seriesUpdate.setDatainicial(request.getParameter("start"));
			seriesUpdate.setDatafinal(request.getParameter("end"));
			
			daoGanttChart.atualizar(seriesUpdate);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}

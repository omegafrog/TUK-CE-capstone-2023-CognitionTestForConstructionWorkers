using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


/// <summary>
/// 게임 결과 변수
/// Go 놓친 횟수 : Go_missed, int 자료형
/// NoGo 클릭 횟수 : NoGo_clicked ,int자료형
/// Go 누른 평균 시간 : AvgResult, string 자료형
/// isDMTPassed 변수 : 게임 통과 여부, bool 자료형
/// </summary>
/// 
///바꿔야 할 것 : 결과 화면 제거, 메뉴 화면에 결과 넘기기


public class DecisionMakingTest_tuto : MonoBehaviour
{


    private GameObject PracticeGamestartPannel;
    private Button PracticeGamestartbutton;

    string GoList, PracticeGoList, PracticeGoNoGoQuestion;//문제 담는 변수

    private GameObject PracticeQuestionDisplayGameobject;//사용자가 입력한 숫자를 유니티 화면에 표시해주는 역할
    private TextMesh PracticeQuestionDisplay; // 사용자가 입력한 숫자를 유니티 화면에 표시해줄 텍스트를 저장할 TextMesh 컴포넌트   


    // Start is called before the first frame update
    void Start()
    {



        PracticeGamestartPannel = GameObject.FindGameObjectWithTag("DMT_PracticeGamestartPanel");
        PracticeGamestartbutton = GameObject.FindGameObjectWithTag("DMT_PracticeGamestartButton").GetComponent<Button>();
        PracticeQuestionDisplay = GameObject.FindGameObjectWithTag("DMT_PracticeQuestionText").GetComponent<TextMesh>();
        PracticeQuestionDisplay.text = "+";


        PracticeGoList = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";
        PracticeGoNoGoQuestion = "ㄹㅓ4";

        PracticeGamestartbutton.onClick.AddListener(PracticeButtonClick);//게임시작
        
    }

    
    // Update is called once per frame
    void Update()
    {
    }


    private void PracticeButtonClick()
    {
        PracticeGamestartbutton.gameObject.SetActive(false);
        PracticeGamestartPannel.SetActive(false);
        StartCoroutine(PracticeGoNoGoGame());
    }

    

    IEnumerator PracticeGoNoGoGame()
    {

        PracticeQuestionDisplay.text = '+'.ToString();
        yield return new WaitForSeconds(1.5f); // 1.5초 동안 처음 십자가 화면 보여줌

        foreach (char q in PracticeGoNoGoQuestion)
        {

            ///Go 에서는 누른 시간, 놓친 여부 측정
            if (PracticeGoList.Contains(q.ToString()))
            {

                PracticeQuestionDisplay.text = q.ToString();
                yield return new WaitForSeconds(0.3f); // 0.3초 동안 문제 보여줌
                PracticeQuestionDisplay.text = "";//문제 사라짐
                yield return new WaitForSeconds(1.8f); // 1.8초 동안 입력 대기

                yield return new WaitForSeconds(0.5f); // 0.5초 동안 휴식
            }
            else
            {

                PracticeQuestionDisplay.text = q.ToString();
                yield return new WaitForSeconds(0.3f); // 0.3초 동안 문제 보여줌
                PracticeQuestionDisplay.text = "";//문제 사라짐
                yield return new WaitForSeconds(1.8f); // 1.8초 동안 입력 대기

                yield return new WaitForSeconds(0.5f); // 0.5초 동안 휴식

            }

        }

        PracticeQuestionDisplay.text = "";//문제 사이 대기 화면 보여줌
        Counting();

        yield break;

    }


    /// <summary>
    /// 버튼이 눌리면 Go, NoGo 2가지로 나눠서 해야 함. 
    /// Go 일때는 누르면 시간 체크, 못누르면 카운트+1
    /// NoGo 일때는 누르면 카운트+1
    /// </summary>
    private void Counting()
    {
       
        SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
    }




}


